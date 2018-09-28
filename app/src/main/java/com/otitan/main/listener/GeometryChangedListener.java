package com.otitan.main.listener;

import android.util.Log;
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
//        setListener();
    }

    @Override
    public void geometryChanged(SketchGeometryChangedEvent event) {
        boolean flag = event.getSource().isSketchValid();
        if(flag){
            setListener();
        }

    }


    private void setListener(){
        Object lo = mapView.getOnTouchListener();
        if (lo instanceof SketchDrawTouchEvent){
            return;
        }
        SketchDrawTouchEvent sketchDrawTouchEvent = new SketchDrawTouchEvent(mapView.getContext(),
                mapView, (View.OnTouchListener) lo,callBack);
        mapView.setOnTouchListener(sketchDrawTouchEvent);
    }
}
