package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.CfysModel

/**
 * 采伐运输 数据管理列表
 */
class CfysDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<CfysModel.Sjgl>()
    val type = ObservableInt(1)
    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}