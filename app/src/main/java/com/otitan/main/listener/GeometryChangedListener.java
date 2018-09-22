package com.otitan.main.listener;

import android.view.View;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchGeometryChangedEvent;
import com.esri.arcgisruntime.mapping.view.SketchGeometryChangedListener;
import com.otitan.base.ValueCallBack;


public class GeometryChangedListener implements SketchGeometryChangedListener {

    private MapView mapView;
    private ValueCallBack<Object> callBack;

    public GeometryChangedListener(MapView mapView,ValueCallBack<Object> callBack){
        this.mapView = mapView;
        this.callBack = callBack;
    }

    @Override
    public void geometryChanged(SketchGeometryChangedEvent event) {
        boolean flag = event.getSource().isSketchValid();
        if(!flag){
            setListener();
        }

    }


    private void setListener(){
        View.OnTouchListener lo = mapView.getOnTouchListener();
        SketchDrawTouchEvent sketchDrawTouchEvent = new SketchDrawTouchEvent(mapView.getContext(), mapView, lo,callBack);
        mapView.setOnTouchListener(sketchDrawTouchEvent);
    }
}
