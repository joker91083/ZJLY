package com.otitan.login

import android.databinding.ObservableField
import android.util.Log
import com.otitan.TitanApplication

import com.otitan.base.BaseViewModel
import com.otitan.main.view.MainActivity
import com.otitan.util.ToastUtil


class LoginModel() : BaseViewModel(),ILoginView{

    constructor(activity: LoginActivity) : this() {
        this.mContext = activity
    }

    constructor(fragment: LoginFragment) :this(){
        this.fragment = fragment
    }

    val TAG="========="

    override fun onSuccess(t: Any?) {
        Log.e(TAG,t.toString())
        ToastUtil.setToast(mContext,t.toString())
    }

    override fun onFail(code: String) {
        Log.e(TAG,code)
        ToastUtil.setToast(mContext,code)
    }

    var name: ObservableField<String>? = ObservableField<String>("")
    var password: ObservableField<String>? = ObservableField<String>("")
    var checked: ObservableField<Boolean>? = ObservableField<Boolean>(true)


    /*登录*/
    fun login() {

        val loginUser = LoginUser()
        loginUser.name = name!!.get()
        loginUser.password = password!!.get()
        loginUser.checked = checked!!.get()


        if (loginUser.name == "admin" && loginUser.password == "admin") {
            onSuccess("用户名和密码正确")
            if(loginUser.checked){
                TitanApplication.sharedPreferences.edit().putBoolean("remember ",true).apply()

                TitanApplication.sharedPreferences.edit().putString("name",loginUser.name).apply()
                TitanApplication.sharedPreferences.edit().putString("password",loginUser.password).apply()
            }

            startActivity(MainActivity::class.java)
        } else if (loginUser.name != "admin" && loginUser.password == "admin") {
            onFail("登录失败,用户名错误");
        } else if (loginUser.name == "admin" && loginUser.password != "admin") {
            onFail("登录失败,密码错误")
        } else {
            onFail("登录失败,用户名和密码错误")
        }


    }


}
