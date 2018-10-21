package com.otitan.main.viewmodel

import android.content.Context
import com.esri.arcgisruntime.geometry.Geometry
import com.otitan.base.BaseViewModel
import com.otitan.base.ValueCallBack
import com.otitan.main.fragment.ZyglFragment

class ZyglViewModel() : BaseViewModel(),ValueCallBack<Any>{
    override fun onGeometry(geometry: Geometry) {
    }

    override fun onSuccess(t: Any) {

    }


    override fun onFail(code: String) {

    }

    constructor(context: Context):this(){
        this.mContext = context
    }

    constructor(fragment: ZyglFragment):this(){
        this.fragment = fragment
    }



}
