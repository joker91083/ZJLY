package com.otitan.main.viewmodel;

import android.content.Context;

import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.esri.arcgisruntime.mapping.view.SketchStyle;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.util.ListenableList;
import com.otitan.base.BaseViewModel;
import com.otitan.main.listener.GeometryChangedListener;
import com.otitan.main.model.Location;
import com.otitan.util.Constant;
import com.otitan.util.ToastUtil;

public class ToolViewModel extends BaseViewModel {
    private static Context mContext;

    public static ToolViewModel getInstance(Context context){
        mContext = context;
        return Holder.instance;
    }

    private static class Holder{
        private static final ToolViewModel instance = new ToolViewModel();
    }

    public void showInfo(MapView mapView){
        ToastUtil.setToast(mContext,"地图信息");
        SketchEditor sketchEditor = mapView.getSketchEditor();

        if(sketchEditor == null){
            sketchEditor = new SketchEditor();
            mapView.setSketchEditor(sketchEditor);
        }
        sketchEditor.start(SketchCreationMode.POINT);
    }

    /*当前位置定位*/
    public void myLocation(Location location){
        location.getMapView().setViewpointCenterAsync(location.getMappoint(), Constant.INSTANCE.getDefalutScale());
    }

    /*清除所有标会*/
    public void cleanAllGraphics(MapView mapView){
        ListenableList<GraphicsOverlay> layerList = mapView.getGraphicsOverlays();
        for(int i=0;i<layerList.size();i++){
            GraphicsOverlay overlay = layerList.get(i);
            overlay.getGraphics().clear();
        }
    }

    /*清除Sketch草图*/
    public void cleanSketch(MapView mapView){
        if(mapView.getSketchEditor() != null){
            mapView.getSketchEditor().stop();
            mapView.getSketchEditor().clearGeometry();
        }
        mapView.getCallout().dismiss();
    }

    /*测量距离*/
    public void distance(MapView mapView){
        SketchEditor sketchEditor = mapView.getSketchEditor();

        if(sketchEditor == null){
            sketchEditor = new SketchEditor();
            mapView.setSketchEditor(sketchEditor);
        }
        sketchEditor.start(SketchCreationMode.FREEHAND_LINE);

    }

    /*面积测量*/
    public void area(MapView mapView){
        SketchEditor sketchEditor = mapView.getSketchEditor();

        if(sketchEditor == null){
            sketchEditor = new SketchEditor();
            mapView.setSketchEditor(sketchEditor);
        }
        sketchEditor.start(SketchCreationMode.FREEHAND_POLYGON);
    }

}
