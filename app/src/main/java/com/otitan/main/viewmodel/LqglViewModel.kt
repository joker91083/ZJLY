package com.otitan.main.viewmodel

import android.content.Context
import com.esri.arcgisruntime.geometry.Geometry
import com.otitan.base.BaseViewModel
import com.otitan.base.ValueCallBack
import com.otitan.main.fragment.LqglFragment

class LqglViewModel() : BaseViewModel(),ValueCallBack<Any>{
    override fun onGeometry(geometry: Geometry) {
    }

    override fun onSuccess(t: Any) {
    }

    override fun onFail(code: String) {
    }

    constructor(mContext: Context) : this(){
        this.mContext = mContext
    }

    constructor(fragment: LqglFragment) : this(){
        this.fragment = fragment
    }



}
