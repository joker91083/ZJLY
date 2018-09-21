package com.otitan.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.otitan.main.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zs on 2016/5/9.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fmList = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.fmList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createForMain(position);
    }

    @Override
    public int getCount() {
        return fmList.size();
    }

}
