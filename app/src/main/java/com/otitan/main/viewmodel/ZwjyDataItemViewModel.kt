package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.ZwjyModel

/**
 * 林权 数据管理列表
 */
class ZwjyDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<ZwjyModel.Sjgl>()
    val type = ObservableInt(1)
    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}