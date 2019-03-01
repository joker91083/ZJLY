package com.otitan.main.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otitan.TitanApplication;
import com.otitan.base.BaseFragment;
import com.otitan.main.viewmodel.SettingViewModel;
import com.otitan.zjly.BR;
import com.otitan.zjly.R;
import com.otitan.zjly.databinding.FmSettingBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 */
public class SettingFragment extends BaseFragment<FmSettingBinding, SettingViewModel> {

    private SettingViewModel viewModel;

    @Override
    public int initContentView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fm_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewmodel;
    }

    @NotNull
    @Override
    public SettingViewModel initViewModel() {
        if (viewModel == null) {
            viewModel = new SettingViewModel(getActivity());
        }
        return viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        setHasOptionsMenu(true);
        Toolbar toolbar = getBinding().toolbar;
        toolbar.setTitle("我的设置");
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }
        SharedPreferences sp = TitanApplication.Companion.getSharedPreferences();
        viewModel.guiji.set(sp.getBoolean("guiji", true));
        viewModel.showLine.set(sp.getBoolean("showLine", false));
    }
}
