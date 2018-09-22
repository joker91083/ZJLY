package com.otitan.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.PersonalViewModel
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmMainBinding

/**
 */
class PersonalFragment : BaseFragment<FmMainBinding, PersonalViewModel>() {

    var personalViewModel: PersonalViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_persion
    }

    override fun initVariableId(): Int {
        return BR.personalViewModel
    }

    override fun initViewModel(): PersonalViewModel {
        if (personalViewModel == null) {
            personalViewModel = PersonalViewModel(this,activity)
        }
        return personalViewModel as PersonalViewModel
    }

    companion object {
        private var personalFragment: PersonalFragment?= null
        @JvmStatic

        @Synchronized fun getInstance(): PersonalFragment?{
            if(personalFragment == null){
                personalFragment = PersonalFragment()
            }
            return personalFragment
        }
    }


}
