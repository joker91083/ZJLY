package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.LQuanModel
import com.otitan.model.LycyModel

/**
 * 林权 数据管理列表
 */
class LQuanDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<LQuanModel.Sjgl>()
    val type = ObservableInt(1)

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}