package com.otitan.main.viewmodel;

import android.util.Log;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.GeodatabaseFeatureTable;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.otitan.model.MyLayer;
import com.otitan.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SketchEditorViewModel {

    private MapView mapView;

    public SketchEditorViewModel(MapView mapView) {
        this.mapView = mapView;
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
    public void addFeature(final MyLayer myLayer, final Geometry geometry) {
        try {
            Feature feature = Objects.requireNonNull(myLayer.getTable()).createFeature();
            feature.setGeometry(geometry);
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
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        Log.e("tag", "小班添加失败：" + e);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("tag", "添加小班错误：" + e);
        }

    }

    /*修班保存*/
    public void editor(MyLayer myLayer, Geometry geometry, Feature feature) {
        try {
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
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            Log.e("tag", "小班修改失败：" + e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e("tag", "修班异常：" + e);
        }
    }

    public void attEdit(Feature feature, MyLayer myLayer) {
        final ListenableFuture<Void> future = Objects.requireNonNull(myLayer.getTable()).updateFeatureAsync(feature);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    if (future.isDone()) {
                        //添加成功
                        Log.e("tag", "小班修改成功");
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("tag", "小班选择失败：" + e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Log.e("tag", "小班选择失败：" + e);
                }
            }
        });
    }


}
