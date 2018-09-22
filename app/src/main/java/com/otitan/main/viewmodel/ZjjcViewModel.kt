package com.otitan.main.viewmodel

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.base.ValueCallBack
import com.otitan.main.fragment.ZjjcFragment

class ZjjcViewModel() : BaseViewModel(), ValueCallBack<Any> {


    override fun onSuccess(o: Any) {

    }

    override fun onFail(code: String) {

    }

    constructor(context: Context) : this(){
        this.mContext = context
    }

    constructor(fragment: ZjjcFragment) :this(){
        this.fragment = fragment
    }
}
