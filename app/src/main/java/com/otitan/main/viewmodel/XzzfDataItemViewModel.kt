package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.XzzfModel

/**
 * 行政执法 数据管理列表
 */
class XzzfDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<XzzfModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}