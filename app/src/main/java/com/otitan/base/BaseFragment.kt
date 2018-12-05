package com.otitan.base

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.properties.Delegates

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), IBaseActivity {
    protected var binding: V by Delegates.notNull()
    protected var viewModel: VM by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false)
        viewModel = initViewModel()
        binding.setVariable(initVariableId(), viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        initViewObservable()

        viewModel.onCreate()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        binding.unbind()
    }

    //刷新布局
    fun refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel)
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    abstract fun initViewModel(): VM

    override fun initParam() {}

    override fun initData() {}

    override fun initViewObservable() {}

    fun onBackPressed(): Boolean {
        return false
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    fun startContainerActivity(canonicalName: String) {
        context?.let {
            val intent = Intent(it, ContainerActivity::class.java)
            intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
            it.startActivity(intent)
        }
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(canonicalName: String, bundle: Bundle) {
        context?.let {
            val intent = Intent(it, ContainerActivity::class.java)
            intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
            intent.putExtra(ContainerActivity.BUNDLE, bundle)
            it.startActivity(intent)
        }
    }

}