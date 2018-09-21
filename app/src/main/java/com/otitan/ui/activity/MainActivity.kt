package com.otitan.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.android.databinding.library.baseAdapters.BR
import com.otitan.base.BaseActivity
import com.otitan.ui.adapter.MyFragmentPagerAdapter
import com.otitan.ui.fragment.LQGLFragment
import com.otitan.ui.mview.IMain
import com.otitan.ui.vm.MainViewModel
import com.otitan.zjly.R
import com.otitan.zjly.databinding.ActivityMainBinding
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 林情概览主页
 */
class MainActivity:BaseActivity<ActivityMainBinding,MainViewModel>(),IMain{
    private var viewmodel:MainViewModel?=null
    private val fragList = ArrayList<Fragment>()
    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        if (viewmodel==null){
            viewmodel = MainViewModel(this,this)
        }
        return viewmodel as MainViewModel
    }

    override fun initData() {
        super.initData()
        val lqgl = LQGLFragment()
        fragList.add(lqgl)

        viewpager.adapter = MyFragmentPagerAdapter(supportFragmentManager,fragList)
        viewpager.currentItem = 0
    }
}