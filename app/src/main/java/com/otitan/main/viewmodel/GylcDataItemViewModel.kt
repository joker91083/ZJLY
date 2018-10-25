package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.GylcModel

/**
 * 国有林场 数据管理列表
 */
class GylcDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<GylcModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}