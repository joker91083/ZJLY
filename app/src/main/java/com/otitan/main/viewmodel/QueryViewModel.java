package com.otitan.main.viewmodel;

import android.content.Context;

import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.otitan.base.BaseViewModel;

public class QueryViewModel extends BaseViewModel{

    private static Context mContext;

    private static class Holder{
        private static QueryViewModel instance = new QueryViewModel();
    }

    private static QueryViewModel getInstance(Context context){
        mContext = context;
        return Holder.instance;
    }


    public void queryGeotry(FeatureLayer featureLayer,Point point){
        QueryParameters parameters = new QueryParameters();
        parameters.setReturnGeometry(true);
        parameters.setGeometry(point);

        featureLayer.getFeatureTable().queryFeaturesAsync(parameters);
        featureLayer.getFeatureTable().addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {

            }
        });
    }





}
