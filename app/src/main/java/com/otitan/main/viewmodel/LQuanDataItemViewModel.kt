package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import com.otitan.base.BaseViewModel
import com.otitan.main.adapter.DataDetailsAdapter
import com.otitan.main.utils.ItemUtils
import com.otitan.model.LQuanModel
import com.otitan.util.MaterialDialogUtil

/**
 * 林权 数据管理列表
 */
class LQuanDataItemViewModel() : BaseViewModel() {

    val lqzb = ObservableField<LQuanModel.Lqzb>()
    val djb = ObservableField<LQuanModel.Djb>()

    val type = ObservableInt(1)

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick() {
        mContext?.let {
            var itemType = "lq"
            if (type.get() == 2) {
                itemType = "lq_2"
            }
            val obj: Any? = if (type.get() == 1) lqzb.get() else djb.get()
            val adapter = DataDetailsAdapter(mContext, ItemUtils.getList(mContext, obj, itemType))
            val layoutManager = LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false)
            MaterialDialogUtil.showItemDetailsDialog(it, "详情", adapter, layoutManager).show()
        }
    }
}