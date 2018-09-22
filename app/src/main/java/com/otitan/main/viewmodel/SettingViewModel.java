package com.otitan.main.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.provider.Settings;
import android.util.Log;

import com.otitan.TitanApplication;
import com.otitan.base.BaseViewModel;

public class SettingViewModel extends BaseViewModel {
    //是否记录轨迹 默认记录 true是 false否
    public ObservableBoolean guiji = new ObservableBoolean(true);
    //是否显示轨迹 默认不显示 true是 false否
    public ObservableBoolean showLine = new ObservableBoolean(false);
    private SharedPreferences sharedPreferences;

    public SettingViewModel(Context context) {
        setMContext(context);
        sharedPreferences = TitanApplication.Companion.getSharedPreferences();
    }

    public void saveGuiji() {
        sharedPreferences.edit().putBoolean("guiji", guiji.get()).apply();
    }

    public void showLine() {
        sharedPreferences.edit().putBoolean("showLine", showLine.get()).apply();
    }

    public void setGps() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        if (getMContext() != null) {
            getMContext().startActivity(intent);
        }
    }
}
