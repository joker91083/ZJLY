package com.otitan.main.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.otitan.base.BaseViewModel;
import com.otitan.main.model.UpInfo;
import com.otitan.util.Constant;
import com.otitan.util.ConverterUtils;

import java.util.ArrayList;
import java.util.UUID;

public class UpinfoViewModel extends BaseViewModel{

    private static Context mContext;

    public ObservableField<String> lon = new ObservableField<>();
    public ObservableField<String> lat = new ObservableField<>();
    public ObservableField<String> addr = new ObservableField<>();
    public ObservableField<String> discrip = new ObservableField<>();
    public ObservableField<String> remark = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>();
    public ObservableField<String> uid = new ObservableField<>();
    public ObservableField<ArrayList<String>> picList = new ObservableField<>(new ArrayList<String>());

    public static IUpinfo iUpinfo;

    private UpInfo upInfo;

    private static class Holder{
        private static final UpinfoViewModel instance = new UpinfoViewModel();
    }

    public static UpinfoViewModel getInstance(Context context,IUpinfo view){
        iUpinfo = view;
        mContext = context;
        return Holder.instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        upInfo = new UpInfo();

    }


    /*选择图片*/
    public void showSelPic(){
        iUpinfo.showSlectPic();
    }


    /*确定按钮*/
    public void sure(){
        upInfo.setLon(Constant.INSTANCE.getSFormat().format(ConverterUtils.toDouble(lon.get())));
        upInfo.setLat(Constant.INSTANCE.getSFormat().format(ConverterUtils.toDouble(lat.get())));
        upInfo.setAddr(addr.get());
        upInfo.setDiscrip(discrip.get());
        upInfo.setRemark(remark.get());
        String uid = UUID.randomUUID().toString().replace("-","");
        upInfo.setUid(uid);

        iUpinfo.sure(upInfo);
    }

    /*返回*/
    public void finish(){
        iUpinfo.closeActivity();
    }

}
