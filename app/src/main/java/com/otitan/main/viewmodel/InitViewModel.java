package com.otitan.main.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.OpenStreetMapLayer;
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SpatialReferenceChangedEvent;
import com.esri.arcgisruntime.mapping.view.SpatialReferenceChangedListener;
import com.otitan.base.BaseViewModel;
import com.otitan.main.model.Location;
import com.otitan.ui.mview.IMap;
import com.otitan.util.Constant;
import com.otitan.util.ResourcesManager;
import com.otitan.util.SpatialUtil;
import com.otitan.util.ToastUtil;
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


    public ArcGISTiledLayer addTileLayer(final MapView mapView){
        String path = ResourcesManager.Companion.getInstances(mContext).getTitlePath();
        if(path.equals("") || path.equals(Constant.INSTANCE.getFilePath())){
            path = mContext.getResources().getString(R.string.World_Imagery);
        }

        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(path);
        Basemap basemap = new Basemap(tiledLayer);
        ArcGISMap gisMap = new ArcGISMap(basemap);
        gisMap.addLoadStatusChangedListener(new LoadStatusChangedListener() {
            @Override
            public void loadStatusChanged(LoadStatusChangedEvent loadStatusChangedEvent) {
                String mapLoadStatus = loadStatusChangedEvent.getNewLoadStatus().name();
                switch (mapLoadStatus) {
                    case "LOADING":
                        Log.e("tag", "LOADING");
                        break;
                    case "FAILED_TO_LOAD":
                        ToastUtil.setToast(mContext, "图层加载异常");
                        Log.e("tag", "图层加载异常");
                        break;
                    case "NOT_LOADED":
                        Log.e("tag", "NOT_LOADED");
                        break;
                    case "LOADED": {
                        Log.e("tag", "图层加载完成");
                        SpatialUtil.Companion.setDefaultSpatialReference(mapView.getSpatialReference());
//                        iMap.setSpatial(mapView.getSpatialReference());
                    }
                }
            }
        });
        mapView.setMap(gisMap);
        return tiledLayer;
    }

    public OpenStreetMapLayer addOpenStreetMapLayer(MapView mapView){
        OpenStreetMapLayer layer = new OpenStreetMapLayer();
        ArcGISMap map = new ArcGISMap();
        map.getBasemap().getBaseLayers().add(layer);
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
