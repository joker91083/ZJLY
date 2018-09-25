package com.otitan.main.viewmodel;

import android.content.Context;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.OpenStreetMapLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.otitan.base.BaseViewModel;
import com.otitan.main.model.Location;
import com.otitan.util.Constant;
import com.otitan.util.ResourcesManager;
import com.otitan.zjly.R;

public class InitViewModel extends BaseViewModel{

    private static Context mContext;

    public static InitViewModel getInstance(Context context){
        mContext = context;
        return Holder.instance;
    }

    private static class Holder{
       private static final InitViewModel instance = new InitViewModel();
    }


    public ArcGISTiledLayer addTileLayer(MapView mapView){
        String path = ResourcesManager.Companion.getInstances(mContext).getTitlePath();
        if(path.equals("") || path.equals(Constant.INSTANCE.getFilePath())){
            path = mContext.getResources().getString(R.string.World_Imagery);
        }

        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(path);
        Basemap basemap = new Basemap(tiledLayer);
        ArcGISMap gisMap = new ArcGISMap(basemap);
        mapView.setMap(gisMap);
        return tiledLayer;
    }

    public OpenStreetMapLayer addOpenStreetMapLayer(MapView mapView){
        OpenStreetMapLayer layer = new OpenStreetMapLayer();
        ArcGISMap map = new ArcGISMap();
        map.getOperationalLayers().add(layer);
        mapView.setMap(map);
        return layer;
    }

    public Location initGisLocation(final MapView mapView){
        final Location location = new Location();

        final LocationDisplay display = mapView.getLocationDisplay();
        display.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
            @Override
            public void onLocationChanged(LocationDisplay.LocationChangedEvent event) {
                Point point = event.getLocation().getPosition();
                if(point != null){
                    location.setGpspoint(point);
                    mapView.setViewpointCenterAsync(point,Constant.INSTANCE.getDefalutScale());
                }

                Point map = display.getMapLocation();
                if(map != null){
                    location.setMappoint(map);
                }


            }
        });
        display.startAsync();
        location.setMapView(mapView);
        return location;
    }

}
