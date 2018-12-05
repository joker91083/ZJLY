package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import com.otitan.base.BaseViewModel
import com.otitan.main.adapter.DataDetailsAdapter
import com.otitan.main.utils.ItemUtils
import com.otitan.model.SlfhModel
import com.otitan.util.MaterialDialogUtil

/**
 * 森林防火 数据管理列表
 */
class SlfhDataItemViewModel() : BaseViewModel() {

    val type = ObservableInt(1)
    val sjgl = ObservableField<SlfhModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick() {
        mContext?.let {
            val adapter = DataDetailsAdapter(mContext, ItemUtils.getList(mContext, sjgl.get(),"slfh"))
            val layoutManager = LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false)
            MaterialDialogUtil.showItemDetailsDialog(it, "详情", adapter, layoutManager).show()
        }
    }
}