package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.LdzzModel
import com.otitan.model.SlfhModel

/**
 * 森林防火 数据管理列表
 */
class LdzzDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<LdzzModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){

    }
}