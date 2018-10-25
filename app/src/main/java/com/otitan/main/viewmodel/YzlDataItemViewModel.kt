package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.YzlModel

/**
 * 营造林 数据管理列表
 */
class YzlDataItemViewModel() : BaseViewModel() {

    val type = ObservableInt(1)
    val wcqk = ObservableField<YzlModel.Wcqk>()
    val jhrw = ObservableField<YzlModel.Jhrw>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick() {

    }
}