package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import com.otitan.base.BaseViewModel
import com.otitan.main.adapter.DataDetailsAdapter
import com.otitan.main.utils.ItemUtils
import com.otitan.model.YzlModel
import com.otitan.util.MaterialDialogUtil

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
//        mContext?.let {
//            val obj: Any? = if (type.get() == 1) wcqk.get() else jhrw.get()
//            val adapter = DataDetailsAdapter(mContext, ItemUtils.getList(mContext, obj, "yzl"))
//            val layoutManager = LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false)
//            MaterialDialogUtil.showItemDetailsDialog(it, "详情", adapter, layoutManager).show()
//        }
    }
}