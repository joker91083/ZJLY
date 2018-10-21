package com.otitan.main.viewmodel;

import android.content.Context;
import android.util.Log;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.otitan.TitanApplication;
import com.otitan.main.model.RepealInfo;
import com.otitan.model.MyLayer;
import com.otitan.util.ToastUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SketchEditorViewModel {

    private MapView mapView;
    private Context context;

    public SketchEditorViewModel(MapView mapView) {
        this.mapView = mapView;
        this.context = mapView.getContext();
    }

    public void setSketch(SketchCreationMode creationMode) {
        SketchEditor sketchEditor = mapView.getSketchEditor();
        if (sketchEditor == null) {
            sketchEditor = new SketchEditor();
            mapView.setSketchEditor(sketchEditor);
        }
        sketchEditor.start(creationMode);
    }

    /*添加小班*/
    public void addFeature(final MyLayer myLayer, final Geometry geometry, Map<String, Object> map) {
        try {
            final Feature feature;
            if (map != null) {
                feature = Objects.requireNonNull(myLayer.getTable()).createFeature(map, geometry);
            } else {
                feature = Objects.requireNonNull(myLayer.getTable()).createFeature();
                feature.setGeometry(geometry);
            }
            final ListenableFuture<Void> result = myLayer.getTable().addFeatureAsync(feature);
            result.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        result.get();
                        if (result.isDone()) {
                            //添加成功
                            Log.e("tag", "小班添加成功");
                            mapView.getSketchEditor().clearGeometry();
                            String id = String.valueOf(feature.getAttributes().get("objectid"));
                            Log.e("tag", "id:" + id);
                            addRepealInfo(feature, myLayer.getTable(), "add", null);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        Log.e("tag", "小班添加失败：" + e);
                    }
                }
            });
        } catch (Exception e) {
            ToastUtil.setToast(context, "添加小班错误：" + e);
            Log.e("tag", "添加小班错误：" + e);
        }
    }

    /*共边增班*/
    public void addFeatureGb(MyLayer myLayer, Geometry geometry, Feature feature) {
        Geometry geometry1 = feature.getGeometry();
        Geometry geometry2 = GeometryEngine.project(geometry, feature.getFeatureTable().getSpatialReference());
        boolean flag = GeometryEngine.intersects(geometry1, geometry2);
        if (flag) {
            Geometry geometry3 = GeometryEngine.difference(geometry2, geometry1);
            addFeature(myLayer, geometry3, feature.getAttributes());
        } else {
            ToastUtil.setToast(context, "新增小班没有与选择小班共边");
        }
    }

    /*删除小班*/
    public void delFeature(final MyLayer myLayer, final Feature feature, final List<Feature> features) {
        try {
            final ListenableFuture<Void> future = myLayer.getTable().deleteFeatureAsync(feature);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        future.get();
                        if (future.isDone()) {
                            Log.e("tag", "小班删除成功");
                            features.remove(feature);
                            addRepealInfo(feature, myLayer.getTable(), "del", null);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            Log.e("tag", "删除小班失败：" + e);
        }
    }

    /*小班合并*/
    public void hebing(MyLayer myLayer, List<Feature> features, Feature feature) {
        List<Geometry> list = new ArrayList<>();
        for (Feature f : features) {
            list.add(f.getGeometry());
        }
        Geometry allGeometry = GeometryEngine.union(list);
        feature.setGeometry(allGeometry);
        final ListenableFuture<Void> future = myLayer.getTable().updateFeatureAsync(feature);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    if (future.isDone()) {
                        Log.e("tag", "小班合并成功");
                        //TODO 合并之后的撤销信息
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    Log.e("tag", "小班合并失败：" + e);
                }
            }
        });
    }

    /*小班切割*/
    public void qiege(MyLayer myLayer, Geometry geometry, Feature feature) {
        Geometry firstG = feature.getGeometry();
        Geometry geometry2 = GeometryEngine.project(geometry, feature.getFeatureTable().getSpatialReference());
        boolean flag = GeometryEngine.intersects(feature.getGeometry(), geometry2);
        if (!flag) {
            ToastUtil.setToast(context, "两个图斑没有交集");
            return;
        }
        Polyline polyline = (Polyline) GeometryEngine.project(geometry, feature.getFeatureTable().getSpatialReference());
        List<Geometry> list = GeometryEngine.cut(feature.getGeometry(), polyline);
        Geometry maxGeometry = null;
        if (list.size() > 1) {
            maxGeometry = list.get(0);
        }
        for (Geometry g : list) {
            if (maxGeometry != null && !maxGeometry.isEmpty() &&
                    GeometryEngine.area((Polygon) g) > GeometryEngine.area((Polygon) maxGeometry)) {
                maxGeometry = g;
            }
        }
        ArrayList<Geometry> tempList = new ArrayList<>(list);
        tempList.remove(maxGeometry);
        if (maxGeometry != null && !maxGeometry.isEmpty()) {
            feature.setGeometry(maxGeometry);
            upDateFeature(myLayer, feature, firstG);
        }
        for (Geometry g : tempList) {
            addFeature(myLayer, g, feature.getAttributes());
        }
    }

    /*修班保存*/
    public void editor(MyLayer myLayer, Geometry geometry, Feature feature) {
        try {
            Geometry firstG = feature.getGeometry();
            Polyline polyline = (Polyline) GeometryEngine.project(geometry, feature.getFeatureTable().getSpatialReference());
            List<Geometry> list = GeometryEngine.cut(feature.getGeometry(), polyline);
            Geometry geometry1 = null;
            if (list.size() > 1) {
                geometry1 = list.get(0);
            }
            for (Geometry g : list) {
                if (geometry1 != null && !geometry1.isEmpty() &&
                        GeometryEngine.area((Polygon) g) > GeometryEngine.area((Polygon) geometry1)) {
                    geometry1 = g;
                }
            }
            if (geometry1 != null && !geometry1.isEmpty()) {
                feature.setGeometry(geometry1);
                upDateFeature(myLayer, feature, firstG);
            }
        } catch (Exception e) {
            ToastUtil.setToast(context, "修班异常：" + e);
            Log.e("tag", "修班异常：" + e);
        }
    }

    private void upDateFeature(final MyLayer myLayer, final Feature feature, final Geometry geometry) {
        final ListenableFuture<Void> future = Objects.requireNonNull(myLayer.getTable()).updateFeatureAsync(feature);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    if (future.isDone()) {
                        //添加成功
                        Log.e("tag", "小班修改成功");
                        mapView.getSketchEditor().clearGeometry();
                        addRepealInfo(feature, myLayer.getTable(), "update", geometry);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    Log.e("tag", "小班修改失败：" + e);
                }
            }
        });
    }

    /**
     * 小班选择
     *
     * @param myLayer
     * @param geometry
     * @param features
     */
    public void queryFeature(MyLayer myLayer, Geometry geometry, final List<Feature> features) {
        QueryParameters parameters = new QueryParameters();
        parameters.setGeometry(geometry);
        parameters.setReturnGeometry(true);
        parameters.setSpatialRelationship(QueryParameters.SpatialRelationship.INTERSECTS);
        FeatureLayer layer = myLayer.getLayer();
        layer.setSelectionWidth(5);
        final ListenableFuture<FeatureQueryResult> listenableFuture = layer.selectFeaturesAsync(parameters, FeatureLayer.SelectionMode.NEW);
        listenableFuture.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    FeatureQueryResult result = listenableFuture.get();
                    Iterator<Feature> it = result.iterator();
                    Feature queryFeature;
                    while (it.hasNext()) {
                        queryFeature = it.next();
                        if (!features.contains(queryFeature)) {
                            Log.e("tag", "小班选择成功");
                            features.add(queryFeature);
                            mapView.getSketchEditor().clearGeometry();
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    ToastUtil.setToast(context, "小班选择失败：" + e);
                    Log.e("tag", "小班选择失败：" + e);
                }
            }
        });
    }

    /**
     * 撤销
     */
    public void repealEdit() {
        ArrayList<RepealInfo> list = TitanApplication.Companion.getRepealInfoList();
        if (list.isEmpty()) {
            ToastUtil.setToast(context, "无法回退");
            return;
        }
        final RepealInfo repealInfo = list.get(list.size() - 1);
        if (repealInfo.getType().equals("add")) {
            final ListenableFuture<Void> future = repealInfo.getTable().deleteFeatureAsync(repealInfo.getFeature());
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        future.get();
                        if (future.isDone()) {
                            TitanApplication.Companion.getRepealInfoList().remove(repealInfo);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        Log.e("tag", "add回退失败：" + e);
                    }
                }
            });
        } else if (repealInfo.getType().equals("del")) {
            final ListenableFuture<Void> future = repealInfo.getTable().addFeatureAsync(repealInfo.getFeature());
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        future.get();
                        if (future.isDone()) {
                            TitanApplication.Companion.getRepealInfoList().remove(repealInfo);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        Log.e("tag", "del回退失败：" + e);
                    }
                }
            });
        } else if (repealInfo.getType().equals("update")) {
            Feature feature = repealInfo.getFeature();
            feature.setGeometry(repealInfo.getGeometry());
            final ListenableFuture<Void> future = repealInfo.getTable().updateFeatureAsync(repealInfo.getFeature());
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        future.get();
                        if (future.isDone()) {
                            TitanApplication.Companion.getRepealInfoList().remove(repealInfo);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        Log.e("tag", "update回退失败：" + e);
                    }
                }
            });
        }
    }

    /**
     * 添加小班修改信息
     *
     * @param feature
     * @param table
     * @param type
     */
    public void addRepealInfo(Feature feature, FeatureTable table, String type, Geometry geometry) {
        RepealInfo repealInfo = new RepealInfo();
        repealInfo.setFeature(feature);
        repealInfo.setTable(table);
        repealInfo.setType(type);
        repealInfo.setGeometry(geometry);
        TitanApplication.Companion.getRepealInfoList().add(repealInfo);
    }
}
