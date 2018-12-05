package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import com.otitan.base.BaseViewModel
import com.otitan.main.adapter.DataDetailsAdapter
import com.otitan.main.utils.ItemUtils
import com.otitan.model.CfysModel
import com.otitan.util.MaterialDialogUtil

/**
 * 采伐运输 数据管理列表
 */
class CfysDataItemViewModel() : BaseViewModel() {

    val cfz = ObservableField<CfysModel.Cfz>()
    val ysz = ObservableField<CfysModel.Ysz>()
    val type = ObservableInt(1)
    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){
        mContext?.let {
            val obj: Any? = if (type.get() == 1) cfz.get() else ysz.get()
            val adapter = DataDetailsAdapter(mContext, ItemUtils.getList(mContext,obj,"cfys"))
            val layoutManager = LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false)
            MaterialDialogUtil.showItemDetailsDialog(it, "详情", adapter, layoutManager).show()
        }
    }
}