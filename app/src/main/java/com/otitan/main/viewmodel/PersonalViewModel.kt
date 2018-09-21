package com.otitan.main.viewmodel

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.base.ValueCallBack
import com.otitan.main.fragment.PersonalFragment
import com.otitan.util.ToastUtil
import com.otitan.zjly.R
import com.titan.versionupdata.VersionUpdata

class PersonalViewModel() : BaseViewModel(), ValueCallBack<Any> {

    var versioncode: String ?= ""

    override fun onSuccess(t: Any) {

    }

    override fun onFail(code: String) {

    }

    constructor(context: Context) :this(){
        this.mContext = context
    }

    constructor(fragment: PersonalFragment) :this(){
        this.fragment = fragment
    }

    override fun onCreate() {
        super.onCreate()

        versioncode = VersionUpdata.getVersionCode(this.fragment!!.activity).toString()

    }


    /*个人中心===版本更新*/
    fun updataVersion(){
        var url = this.mContext?.getString(R.string.updata_url)

        var flag = VersionUpdata(this.fragment?.activity!!).checkVersion(url)
        if(!flag){
            ToastUtil.setToast(this.fragment!!.activity,"已经是最新版本")
        }
    }

    /*个人中心===密码修改*/
    fun updataPassword(){

    }

    /*个人中心===设置*/
    fun setting(){

    }

    /*个人中心===扫码登录*/
    fun slogin(){

    }





}
