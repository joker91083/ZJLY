package com.otitan.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.otitan.zjly.R;

/**
 * Created by whs on 2017/6/30
 */

public class SymbolUtil {
    private Context mContext;
    /**火险等级样式*/
    public static SimpleFillSymbol risk1_Symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.GREEN, null);
    public static SimpleFillSymbol risk2_Symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.BLUE, null);
    public static SimpleFillSymbol risk3_Symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.YELLOW, null);
    public static SimpleFillSymbol risk4_Symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.rgb(255, 165, 58), null);
    public static SimpleFillSymbol risk5_Symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.RED, null);

    /**态势标绘样式*/
    public static MarkerSymbol firepoint;
    /**测量起点样式*/
    public static MarkerSymbol startpoint=new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,Color.BLUE,5);
    //轨迹采集
    public static SimpleLineSymbol measureline=new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,Color.RED,3, SimpleLineSymbol.MarkerStyle.NONE, SimpleLineSymbol.MarkerPlacement.END);
    //轨迹查询
    public static SimpleLineSymbol guijiline=new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,Color.BLUE,3, SimpleLineSymbol.MarkerStyle.NONE, SimpleLineSymbol.MarkerPlacement.END);

    //节点样式
    public static MarkerSymbol vertexSymbol =new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.GREEN,5);
    public SymbolUtil(Context mContext) {
        this.mContext = mContext;
        //firepoint = createAsync((BitmapDrawable) ContextCompat.getDrawable(mContext, R.drawable.plot_firepoint));

    }
    //轨迹线样式
    public static SimpleLineSymbol getLineSymbol(){
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID , Color.parseColor("#1266E6"), 8);
        lineSymbol.setAntiAlias(true);
        return lineSymbol;
    }

    //苗圃图斑边界描边样式
    public static SimpleLineSymbol getNurseryLineSymbol(){
        SimpleLineSymbol symbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,Color.WHITE,1);
        symbol.setAntiAlias(true);
        return symbol;
    }

    //苗圃样式
    public static SimpleFillSymbol getNurserySymbol(){
        return new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID , Color.parseColor("#0C96FF"), getNurseryLineSymbol());
    }
    //地块样式
    public static SimpleFillSymbol getLandSymbol(){
        return new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID , Color.parseColor("#41df81"),null);
    }
    //新增面
    public static SimpleFillSymbol getNewFillSymbol(){
        return new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID , Color.RED,null);
    }

    public static TextSymbol getLengthSymbol(String text){
        TextSymbol lengthtext=new TextSymbol(20,text,Color.RED, TextSymbol.HorizontalAlignment.RIGHT, TextSymbol.VerticalAlignment.BOTTOM);
        return lengthtext;

    }

    public static TextSymbol getAreaText(String text){
        return new TextSymbol(20,text,Color.RED, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.MIDDLE);
    }

    public static MarkerSymbol getLocSymbol(Context context){
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.icon_gcoding));
        pictureMarkerSymbol.setOffsetY(20);
        return pictureMarkerSymbol;
    }
}
