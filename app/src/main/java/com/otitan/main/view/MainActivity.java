package com.otitan.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.otitan.main.fragment.LqglFragment;
import com.otitan.main.fragment.PersonalFragment;
import com.otitan.main.fragment.ZjjcFragment;
import com.otitan.main.fragment.ZyglFragment;
import com.otitan.main.adapter.MainPagerAdapter;
import com.otitan.main.widgets.NoScrollViewPager;
import com.otitan.zjly.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;
    @BindView(R.id.main_lqgl)
    RadioButton mainLqgl;
    @BindView(R.id.main_zjjc)
    RadioButton mainZjjc;
    @BindView(R.id.main_zybj)
    RadioButton mainZybj;
    @BindView(R.id.main_grzx)
    RadioButton mainGrzx;
    @BindView(R.id.main_group)
    RadioGroup mainGroup;


    private List<Fragment> fmList = new ArrayList<>();
    MainPagerAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }


    private void initView() {

        fmList.add(new LqglFragment());
        fmList.add(new ZjjcFragment());
        fmList.add(new ZyglFragment());
        fmList.add(new PersonalFragment());

        mainViewpager.addOnPageChangeListener(this);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fmList);
        if (adapter != null) {
            mainViewpager.setAdapter(adapter);
            mainViewpager.setCurrentItem(0);
            mainViewpager.setOffscreenPageLimit(5);
        }

        mainLqgl.setChecked(true);
        mainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_lqgl:
                        mainViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.main_zjjc:
                        mainViewpager.setCurrentItem(1, false);
                        break;
                    case R.id.main_grzx:
                        mainViewpager.setCurrentItem(3, false);
                        break;
                }
            }
        });

    }

    @OnClick(R.id.main_zybj)
    public void toActivity(){
        startToActivity(MapCenterActivity.class);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mainLqgl.setChecked(true);
                break;
            case 1:
                mainZjjc.setChecked(true);
                break;
            case 2:
                mainZybj.setChecked(true);
                startToActivity(MapCenterActivity.class);
                break;
            case 3:
                mainGrzx.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void startToActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }
}
