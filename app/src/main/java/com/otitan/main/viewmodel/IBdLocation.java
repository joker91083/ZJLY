package com.otitan.main.viewmodel;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

public interface IBdLocation {

    void initBdlocation(BDLocationListener listener);

    LocationClient getClient();

}
