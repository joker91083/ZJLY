package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.SdbhModel

/**
 * 湿地保护 数据管理列表
 */
class SdbhDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<SdbhModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}