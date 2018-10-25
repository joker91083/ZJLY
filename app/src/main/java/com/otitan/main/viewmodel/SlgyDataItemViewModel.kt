package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.SlgyModel

/**
 * 森林公园 数据管理列表
 */
class SlgyDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<SlgyModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}