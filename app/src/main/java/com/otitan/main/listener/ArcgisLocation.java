package com.otitan.main.listener;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.otitan.main.model.Location;

public interface ArcgisLocation {

    Location gisLocation(MapView mapView);
}
