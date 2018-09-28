package com.otitan.main.listener;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.otitan.base.ValueCallBack;
import com.otitan.util.Constant;


public class SketchDrawTouchEvent extends DefaultMapViewOnTouchListener {

    private View.OnTouchListener _listener;
    private DefaultMapViewOnTouchListener _maplistener;
    private SketchEditor sketchEditor;
    private ValueCallBack<Object> callBack;


    public SketchDrawTouchEvent(Context context, MapView mapView, View.OnTouchListener listener,ValueCallBack<Object> callBack) {
        super(context, mapView);
        this.callBack = callBack;
        _listener = listener;
        _maplistener = (DefaultMapViewOnTouchListener) listener;
        this.sketchEditor = mapView.getSketchEditor();
    }


    //    @Override
    public boolean onTouch(View v, MotionEvent e) {
        //这里实现监听选择性调用
        if (e.getAction() == MotionEvent.ACTION_UP) {
            boolean flag = mMapView.getSketchEditor().isSketchValid();
            if(flag){
                Geometry geometry = mMapView.getSketchEditor().getGeometry();
                callBack.onSuccess(geometry);
            }
        }

        if (_maplistener != null) {
            return _maplistener.onTouch(v, e);
        }
        if (_listener != null)
            return _listener.onTouch(v, e);

        return false;
    }
}
