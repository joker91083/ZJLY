package com.otitan.main.listener;

import android.content.Context;

import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

public class MapTouchListener extends DefaultMapViewOnTouchListener {


    public MapTouchListener(Context context, MapView mapView) {
        super(context, mapView);
    }




}
