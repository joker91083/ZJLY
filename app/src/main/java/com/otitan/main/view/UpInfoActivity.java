package com.otitan.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.otitan.base.ActivityUtils;
import com.otitan.base.BaseFragmentActivity;
import com.otitan.base.BaseViewModel;
import com.otitan.base.ViewModelHolder;
import com.otitan.main.fragment.UpinfoFragment;
import com.otitan.main.listener.MybdListener;
import com.otitan.main.viewmodel.UpinfoViewModel;
import com.otitan.util.Constant;
import com.otitan.zjly.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpInfoActivity extends BaseFragmentActivity implements BDLocationListener{

    private UpinfoViewModel upinfoViewModel;
    private UpinfoFragment upinfoFragment;

    private MybdListener mybdListener;
    private BDLocation bdLocation;
    private LocationClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        upinfoFragment = (UpinfoFragment) findOrCreateViewFragment();
        upinfoViewModel = (UpinfoViewModel) findOrCreateViewModel();

        initData();

    }

    void initData() {
        mybdListener = new MybdListener();
        mybdListener.initBdlocation(this);

        double lon = this.getIntent().getDoubleExtra("lon",0);
        double lat = this.getIntent().getDoubleExtra("lat",0);
        upinfoViewModel.lon.set(Constant.INSTANCE.getSFormat().format(lon));
        upinfoViewModel.lat.set(Constant.INSTANCE.getSFormat().format(lat));


    }

    @NotNull
    @Override
    public Fragment findOrCreateViewFragment() {
        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.fram_upinfo);
        if (fragment == null) {
            fragment = UpinfoFragment.getInstance();
            ActivityUtils.addFragmentToActivity(this.getSupportFragmentManager(), fragment, R.id.fram_upinfo);
        }

        return fragment;
    }

    @NotNull
    @Override
    public BaseViewModel findOrCreateViewModel() {
        ViewModelHolder<UpinfoViewModel> fragment = (ViewModelHolder<UpinfoViewModel>) this.getSupportFragmentManager().findFragmentByTag(this.getTAG());
        if (fragment == null || fragment.getViewmodel() == null) {
            UpinfoViewModel viewModel = UpinfoViewModel.getInstance(this.getMContext(), upinfoFragment);
            ActivityUtils.addFragmentToActivity(this.getSupportFragmentManager(), ViewModelHolder.createContainer(viewModel), this.getTAG());
            return viewModel;
        }
        return fragment.getViewmodel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_up_info;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mybdListener.getClient() != null) {
            mybdListener.getClient().stop();
        }
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }

        this.bdLocation = bdLocation;
        String addr = bdLocation.getAddrStr();
        if(!addr.equals("")){
            upinfoViewModel.addr.set(addr);
        }


    }


}
