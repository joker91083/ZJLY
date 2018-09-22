package com.otitan.main.viewmodel;

import android.content.Context;

import com.otitan.base.BaseViewModel;

public class GeoViewModel extends BaseViewModel{

    private static Context mContext;

    private static class Holder{
        private static final GeoViewModel instance = new GeoViewModel();
    }

    public static GeoViewModel getInstance(Context context){
        mContext = context;
        return Holder.instance;
    }





}
