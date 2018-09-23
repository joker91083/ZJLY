package com.otitan.main.viewmodel;

import android.content.Context;

import com.otitan.base.BaseViewModel;
import com.otitan.main.view.ILayerView;
import com.otitan.main.view.UpInfoActivity;
import com.otitan.ui.mview.IMap;

public class BootViewModel extends BaseViewModel{

    private static Context mContext;
    private static IMap layerView;

    private static class Holder{
        private static final BootViewModel instance = new BootViewModel();
    }

    public static BootViewModel getInstance(Context context,IMap view){
        mContext = context;
        layerView = view;
        return Holder.instance;
    }

    public void upInfo(){
        startActivity(UpInfoActivity.class);
    }

    public void layerManger(){
        layerView.showTckz();
    }

}
