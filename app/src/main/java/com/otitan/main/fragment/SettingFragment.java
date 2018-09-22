package com.otitan.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.otitan.base.BaseFragment;
import com.otitan.main.viewmodel.SettingViewModel;
import com.otitan.zjly.BR;
import com.otitan.zjly.R;
import com.otitan.zjly.databinding.FmSettingBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 */
public class SettingFragment extends BaseFragment<FmSettingBinding,SettingViewModel> {

    private SettingViewModel viewModel;

    @Override
    public int initContentView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fm_setting;
    }

    @Override
    public int initVariableId() {
        return BR.settingViewModel;
    }

    @NotNull
    @Override
    public SettingViewModel initViewModel() {
        if(viewModel == null){
            viewModel = new SettingViewModel();
        }
        return viewModel;
    }
}
