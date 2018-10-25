package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.YhswModel

/**
 * 有害生物 数据管理列表
 */
class YhswDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<YhswModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}