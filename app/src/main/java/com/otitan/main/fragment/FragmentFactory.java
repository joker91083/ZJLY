package com.otitan.main.fragment;

import android.support.v4.app.Fragment;


/**
 * fragment的工厂类
 * zs on 2015/11/3.
 */
public class FragmentFactory {

    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createById(int resId) {
        Fragment fragment = null;
        switch (resId) {



        }
        return fragment;
    }

    /**
     * main
     *
     * @param position
     * @return
     */
    public static Fragment createForMain(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = LqglFragment.getInstance();
                break;
            case 1:
                fragment = ZjjcFragment.getInstance();
                break;
            case 2:
                fragment = ZyglFragment.getInstance();
                break;
            case 3:
                fragment = PersonalFragment.getInstance();
                break;
        }
        return fragment;
    }

    /**
     * 运营中心
     *
     * @param position
     * @return
     */
    public static Fragment createForOperation(int position) {
        Fragment fragment = null;
        switch (position) {

        }
        return fragment;
    }


    /**
     * 消费中心
     *
     * @param position
     * @return
     */
    public static Fragment createForConsume(int position) {
        Fragment fragment = null;
        switch (position) {

        }
        return fragment;
    }
    /**
     * 管理中心
     *
     * @param position
     * @return
     */
    public static Fragment createForManager(int position) {
        Fragment fragment = null;
        switch (position) {

        }
        return fragment;
    }

    /**
     * 主页fragment
     */
    public static Fragment createForData(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LqglFragment();
                break;
            case 1:
                fragment = new ZjjcFragment();
                break;
            case 2:
                fragment = new ZyglFragment();
                break;
            case 3:
                fragment = new PersonalFragment();
                break;

        }
        return fragment;
    }
}
