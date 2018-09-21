package com.otitan.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.MainViewModel

import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmMainBinding


/**
 *MainFragment
 */
class MainFragment : BaseFragment<FmMainBinding, MainViewModel>() {

    var mainViewModel: MainViewModel? = null

    override fun initVariableId(): Int {
        return com.otitan.zjly.BR.mainViewModel
    }

    override fun initViewModel(): MainViewModel {
        if (mainViewModel == null) {
            mainViewModel = MainViewModel(this)
        }
        return mainViewModel as MainViewModel
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_main
    }


}
