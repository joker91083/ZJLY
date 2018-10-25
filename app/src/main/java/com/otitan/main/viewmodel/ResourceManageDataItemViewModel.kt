package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel
import com.otitan.model.ResourceModel

/**
 * 资源管理 数据管理列表
 */
class ResourceManageDataItemViewModel() : BaseViewModel() {

    var type = ObservableInt(1)
    val dq = ObservableField<String>()
    val ldmj = ObservableField<ResourceModel.LDMJModel>()
    val slmj = ObservableField<ResourceModel.SLMJModel>()
    val gylmj = ObservableField<ResourceModel.GYLMJModel>()
    val slfgl = ObservableField<ResourceModel.SLFGLModel>()
    val hlmxj = ObservableField<ResourceModel.HLMXJModel>()

    constructor(context: Context?) : this() {
        this.mContext = context
//        this.mView = mView
    }

}