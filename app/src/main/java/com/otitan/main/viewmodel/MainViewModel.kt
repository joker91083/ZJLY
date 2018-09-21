package com.otitan.main.viewmodel

import android.content.Context
import android.util.Log

import com.otitan.base.BaseViewModel
import com.otitan.base.ValueCallBack
import com.otitan.main.fragment.MainFragment

class MainViewModel() : BaseViewModel(),ValueCallBack<Any>{


    override fun onFail(code: String) {
        Log.e("============",code)
    }

    override fun onSuccess(t: Any) {
        Log.e("============",t.toString())
    }

    constructor(context: Context) :this(){
        this.mContext = context
    }

    constructor(fragment: MainFragment) :this(){
        this.fragment = fragment
    }

    /*林情概览*/
    fun lqgl(){

    }

    /*资源编辑*/
    fun zybj(){

    }

    /*指尖决策*/
    fun zjjc(){

    }

    /*个人中心*/
    fun grzx(){

    }




}
