package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.LqglViewModel

import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLqglBinding


/**
 *
 */
class LqglFragment : BaseFragment<FmLqglBinding, LqglViewModel>() {

    var lqglViewModel: LqglViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_lqgl
    }

    override fun initVariableId(): Int {
        return com.otitan.zjly.BR.viewmodel
    }

    override fun initViewModel(): LqglViewModel {
        if(lqglViewModel == null){
            lqglViewModel = LqglViewModel(this)
        }
        return lqglViewModel as LqglViewModel
    }

    companion object {
        private var lqglFragment: LqglFragment?= null
        @JvmStatic

        @Synchronized fun getInstance(): Fragment?{
            if(lqglFragment == null){
                lqglFragment = LqglFragment()
            }
            return lqglFragment
        }
    }



}
