package com.otitan.main.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.otitan.base.BaseViewModel;
import com.otitan.util.Constant;
import com.otitan.util.ConverterUtils;

public class CalloutViewModel extends BaseViewModel{

    private static Context mContext;

    private static class Holder{
        private static final CalloutViewModel instance = new CalloutViewModel();
    }

    public static CalloutViewModel getInstance(Context context){
        mContext = context;
        return Holder.instance;
    }

    @SuppressLint("SetTextI18n")
    public void showValueInmap(MapView mapView, Point point, double value, String unit){

        TextView calloutContent = new TextView(mapView.getContext());
        calloutContent.setTextColor(Color.RED);
        calloutContent.setSingleLine();
        // format coordinates to 4 decimal places
        calloutContent.setText(Constant.INSTANCE.getTFormat().format(value)+unit);

        Callout callout = mapView.getCallout();
        callout.setContent(calloutContent);
        callout.setLocation(point);
        callout.show();

    }




}
