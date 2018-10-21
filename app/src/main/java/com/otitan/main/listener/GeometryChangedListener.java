package com.otitan.main.listener;

import android.view.View;

import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchGeometryChangedEvent;
import com.esri.arcgisruntime.mapping.view.SketchGeometryChangedListener;
import com.otitan.base.ValueCallBack;
import com.otitan.main.model.ActionModel;


public class GeometryChangedListener implements SketchGeometryChangedListener {

    private MapView mapView;
    private ValueCallBack<Object> callBack;

    public GeometryChangedListener(MapView mapView, ValueCallBack<Object> callBack){
        this.mapView = mapView;
        this.callBack = callBack;
    }

    @Override
    public void geometryChanged(SketchGeometryChangedEvent event) {
        boolean b = event.getSource().isSketchValid();
        Geometry geometry = event.getGeometry();
        boolean flag = false;
        if(geometry != null){
            flag = (geometry.getGeometryType() == GeometryType.POINT);
        }
        if(b && flag){
            callBack.onGeometry(geometry);
        }else{
            setListener();
        }

    }


    private void setListener(){
        View.OnTouchListener touch = mapView.getOnTouchListener();
        if (touch instanceof SketchDrawTouchEvent){
            return;
        }
        SketchDrawTouchEvent sketchDrawTouchEvent = new SketchDrawTouchEvent(mapView, touch,callBack);
        mapView.setOnTouchListener(sketchDrawTouchEvent);
    }
}
