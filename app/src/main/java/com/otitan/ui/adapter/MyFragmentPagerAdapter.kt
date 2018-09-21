package com.otitan.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

/**
 * Created by Administrator on 2015/6/24.
 *
 * Fragment页面设置Adapter
 */
class MyFragmentPagerAdapter(fm: FragmentManager, lst: List<Fragment>) : FragmentPagerAdapter(fm) {
    private val list = ArrayList<Fragment>()

    init {
        this.list.clear()
        this.list.addAll(lst)
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}
