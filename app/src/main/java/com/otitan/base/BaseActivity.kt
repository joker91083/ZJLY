package com.otitan.base

import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import kotlin.properties.Delegates
import android.view.WindowManager
import android.os.Build
import android.view.View


abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), IBaseActivity {
    protected var binding: V by Delegates.notNull()
    protected var viewModel: VM by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
//        //透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT
//        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initParam()
        initViewDataBinding(savedInstanceState)
        initData()
        initViewObservable()
        viewModel.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        binding.unbind()

    }

    fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        viewModel = initViewModel()
        binding.setVariable(initVariableId(), viewModel)
    }

    //刷新布局
    fun refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel)
        }
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    abstract fun initViewModel(): VM

    override fun initData() {}

    override fun initParam() {}

    override fun initViewObservable() {}
}