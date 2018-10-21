package com.otitan.main.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.otitan.base.BaseViewModel;
import com.otitan.util.Constant;
import com.otitan.zjly.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /*展示小班属性信息*/
    public void showQueryInfo(MapView mapView, Feature feature,Geometry point){

        View view = LayoutInflater.from(mapView.getContext()).inflate(R.layout.callout_geometry_info,null);
        ListView listView = view.findViewById(R.id.callout);
        Map<String,Object> map = feature.getAttributes();
        List<String> attList = new ArrayList<>();
        for (String key:map.keySet()){
            attList.add(key+":"+map.get(key));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,R.layout.item_callout,attList);
        listView.setAdapter(adapter);
        //设置Callout样式
        Callout.Style style = new Callout.Style(mContext);
        style.setMaxWidth(250); //设置最大宽度
        style.setMaxHeight(400);  //设置最大高度
        style.setMinWidth(200);  //设置最小宽度
        style.setMinHeight(100);  //设置最小高度
        style.setBorderWidth(1); //设置边框宽度
        style.setBorderColor(Color.BLUE); //设置边框颜色
        style.setBackgroundColor(Color.WHITE); //设置背景颜色
        style.setCornerRadius(8); //设置圆角半径

        Callout callout = mapView.getCallout();
        callout.setStyle(style);
        callout.setContent(view);
        callout.setLocation(point.getExtent().getCenter());
        callout.show();

        mapView.invalidate();
    }




}
