package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.DataItemModel

/**
 * 数据列表item详情
 */
class DataDetailsItemViewModel() : BaseViewModel() {

    val info = ObservableField<DataItemModel>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick() {

    }
}