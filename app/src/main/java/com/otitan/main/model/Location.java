package com.otitan.main.model;


import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.io.Serializable;

public class Location implements Serializable{
    private static final long serialVersionUID = 2553051057327894035L;

    public Point getGpspoint() {
        return gpspoint;
    }

    public void setGpspoint(Point gpspoint) {
        this.gpspoint = gpspoint;
    }

    public Point getMappoint() {
        return mappoint;
    }

    public void setMappoint(Point mappoint) {
        this.mappoint = mappoint;
    }

    private Point gpspoint;

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    private Point mappoint;
    private MapView mapView;
}
