package com.otitan.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.zjly.BR
import com.otitan.base.BaseFragment
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLoginBinding

class LoginFragment : BaseFragment<FmLoginBinding, LoginModel>() {

    var loginModel: LoginModel? = null


    companion object {
        @JvmStatic
        fun getInstance(): LoginFragment {
            return LoginFragment()
        }
    }


    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {

        return R.layout.fm_login
    }

    override fun initVariableId(): Int {
        return BR.loginModel
    }

    override fun initViewModel(): LoginModel {
        if (loginModel == null) {
            loginModel = LoginModel(this)
        }
        return loginModel as LoginModel
    }


}
