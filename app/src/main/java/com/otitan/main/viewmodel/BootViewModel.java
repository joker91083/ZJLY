package com.otitan.main.viewmodel;

import android.content.Context;

import com.otitan.base.BaseViewModel;
import com.otitan.main.model.UpInfo;
import com.otitan.main.view.UpInfoActivity;

public class BootViewModel extends BaseViewModel{

    private static Context mContext;

    private static class Holder{
        private static final BootViewModel instance = new BootViewModel();
    }

    public static BootViewModel getInstance(Context context){
        mContext = context;
        return Holder.instance;
    }

    public void upInfo(){
        startActivity(UpInfoActivity.class);
    }

    public void layerManger(){

    }

}
