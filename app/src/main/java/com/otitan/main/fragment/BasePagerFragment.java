package com.otitan.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otitan.TitanApplication;
import com.otitan.main.utils.CommonUtils;
import com.otitan.main.widgets.SYViewPager;
import com.otitan.zjly.R;
import com.titan.tabpagerindictor.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zhaoshuo on 2016/5/10.
 */
public abstract class BasePagerFragment extends Fragment {

    @BindView(R.id.indicator)
    public TabPageIndicator indicator;
    @BindView(R.id.viewPager)
    public SYViewPager viewPager;

    private BasePagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.viewpager_indicator, null);
        ButterKnife.bind(this, view);
        setActionBar();
        initViewPager();
        return view;
    }

    private void initViewPager() {

        adapter = new BasePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(10);
        indicator.setViewPager(viewPager);
        setIndicator();
    }

    class BasePagerAdapter extends FragmentStatePagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = setTitles();// 调用setTitles()抽象方法，让子类返回具体的标题数据
        }

        @Override
        public Fragment getItem(int position) {
            return setFragment(position);// 调用setFragment()抽象方法，让子类返回具体的Fragment对象
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    /**
     * 设置指示器的样式，可以通过重写单独设置覆盖
     */
    protected void setIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);
        indicator.setDividerColor(Color.parseColor("#dcdcdc"));// 设置分割线的颜色
        indicator.setDividerPadding(CommonUtils.dip2px(TitanApplication.Companion.getInstances(), 0));
        indicator.setIndicatorColor(Color.parseColor("#44A43B"));// 设置底部横线的颜色
        indicator.setTextColorSelected(Color.parseColor("#44A43B"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#797979"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(CommonUtils.sp2px(TitanApplication.Companion.getInstances(), 14));// 设置字体大小
    }

    protected abstract Fragment setFragment(int position);//设置fragment

    protected abstract String[] setTitles();// 设置标题

    protected abstract void setActionBar();// 设置ActionBar

}
