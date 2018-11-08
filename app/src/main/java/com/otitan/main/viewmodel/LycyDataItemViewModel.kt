package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.LycyModel

/**
 * 林业产业 数据管理列表
 */
class LycyDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<LycyModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}