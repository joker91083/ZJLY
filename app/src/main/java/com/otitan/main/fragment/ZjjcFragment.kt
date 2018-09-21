package com.otitan.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.ZjjcViewModel

import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmZjjcBinding


/**
 *
 */
class ZjjcFragment : BaseFragment<FmZjjcBinding, ZjjcViewModel>() {

    var zjjcViewModel: ZjjcViewModel? = null


    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_zjjc
    }

    override fun initVariableId(): Int {
        return com.otitan.zjly.BR.zjjcViewModel
    }

    override fun initViewModel(): ZjjcViewModel {
        if(zjjcViewModel == null){
            zjjcViewModel = ZjjcViewModel(this)
        }
        return zjjcViewModel as ZjjcViewModel
    }


    companion object {
        private var zjjcFragment: ZjjcFragment?= null

        @JvmStatic
        @Synchronized fun getInstance(): ZjjcFragment?{
            if(zjjcFragment == null){
                zjjcFragment = ZjjcFragment()
            }
            return zjjcFragment
        }
    }


}
