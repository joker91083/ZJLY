package com.otitan.zjly.base;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * Created by sp on 2018/6/7.
 * ViewModel基类
 */
public class BaseViewModel extends BaseObservable {

    protected Context mContext;

    protected DataRepository mDataRepository;

    // 数据加载中
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    // 提示信息
    public final ObservableField<String> snackbarText = new ObservableField<>();

    public String getSnackbarText() {
        return snackbarText.get();
    }
}
