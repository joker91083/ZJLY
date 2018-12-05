package com.otitan.login

import android.databinding.ObservableField
import android.util.Log
import com.esri.arcgisruntime.geometry.Geometry
import com.google.gson.Gson
import com.otitan.TitanApplication

import com.otitan.base.BaseViewModel
import com.otitan.data.DataRepository
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.main.view.MainActivity
import com.otitan.model.LoginInfo
import com.otitan.model.LoginResult
import com.otitan.model.ResultModel
import com.otitan.util.ToastUtil
import okhttp3.MediaType
import okhttp3.RequestBody


class LoginModel() : BaseViewModel(), ILoginView {
    override fun onGeometry(geometry: Geometry) {
    }

    private var dataRepository: DataRepository = Injection.provideDataRepository()

    constructor(activity: LoginActivity) : this() {
        this.mContext = activity
    }

    constructor(fragment: LoginFragment) : this() {
        this.fragment = fragment
    }

    val TAG = "========="

    override fun onSuccess(t: Any?) {
        Log.e(TAG, t.toString())
        ToastUtil.setToast(mContext, t.toString())
    }

    override fun onFail(code: String) {
        Log.e(TAG, code)
        ToastUtil.setToast(mContext, code)
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

        val loginInfo = LoginInfo()
        loginInfo.password = password!!.get()
        loginInfo.username = name!!.get()

        showDialog("登陆中...")
        dataRepository.login(name!!.get()!!, password!!.get()!!, "password", object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                onFail("登录失败:$info")
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                TitanApplication.loginResult = (result as ResultModel<LoginResult>).data
                TitanApplication.instances.registerMobile()
                if (checked!!.get()!!) {
                    val edit = TitanApplication.sharedPreferences.edit()
                    edit.putBoolean("remember ", true).apply()
                    edit.putString("name", name!!.get()).apply()
                    edit.putString("password", password!!.get()).apply()
                    edit.putString("auth", result.data.access_token).apply()
                }
                startActivity(MainActivity::class.java)
            }
        })

//        if (loginUser.name == "admin" && loginUser.password == "admin") {
//            onSuccess("用户名和密码正确")
//            if (loginUser.checked) {
//                TitanApplication.sharedPreferences.edit().putBoolean("remember ", true).apply()
//                TitanApplication.sharedPreferences.edit().putString("name", loginUser.name).apply()
//                TitanApplication.sharedPreferences.edit().putString("password", loginUser.password).apply()
//            }
//
//            startActivity(MainActivity::class.java)
//        } else if (loginUser.name != "admin" && loginUser.password == "admin") {
//            onFail("登录失败,用户名错误")
//        } else if (loginUser.name == "admin" && loginUser.password != "admin") {
//            onFail("登录失败,密码错误")
//        } else {
//            onFail("登录失败,用户名和密码错误")
//        }
    }

}
