package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import org.jetbrains.anko.toast

class ChangePWViewModel():BaseViewModel(){

    val sharedPreferences = TitanApplication.sharedPreferences
    //用户名
    val username = ObservableField<String>(sharedPreferences.getString("name",""))
    //原始密码
    val oldPW = ObservableField<String>()
    //新密码
    val newPW = ObservableField<String>()
    //确认新密码
    val surePW = ObservableField<String>()
    constructor(context: Context?):this(){
        this.mContext = context
    }

    fun sure(){
        val s = username.get()+","+oldPW.get()+","+newPW.get()+","+surePW.get()
        mContext?.toast(s)
    }
}