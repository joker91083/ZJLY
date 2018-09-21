package com.otitan.main.listener;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.otitan.TitanApplication;
import com.otitan.main.viewmodel.IBdLocation;

public class MybdListener implements IBdLocation{

    private LocationClient client;

    @Override
    public void initBdlocation(BDLocationListener listener) {
        client = new LocationClient(TitanApplication.Companion.getInstances());
        client.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setOpenGps(true); // 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型bd09ll gcj02 GCJ-02
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        client.setLocOption(option);
        client.start();

    }


    @Override
    public LocationClient getClient() {
        if(client != null){
            return client;
        }
        return new LocationClient(TitanApplication.Companion.getInstances());
    }


}
