package com.otitan.main.model;

import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.view.MapView;

public class MainModel {

    private MapView mapView;

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public ArcGISTiledLayer getTiledLayer() {
        return tiledLayer;
    }

    public void setTiledLayer(ArcGISTiledLayer tiledLayer) {
        this.tiledLayer = tiledLayer;
    }

    private ArcGISTiledLayer tiledLayer;

}
