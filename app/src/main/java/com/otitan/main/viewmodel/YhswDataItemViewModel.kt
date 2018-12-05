package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import com.otitan.base.BaseViewModel
import com.otitan.main.adapter.DataDetailsAdapter
import com.otitan.main.utils.ItemUtils
import com.otitan.model.YhswModel
import com.otitan.util.MaterialDialogUtil

/**
 * 有害生物 数据管理列表
 */
class YhswDataItemViewModel() : BaseViewModel() {

    val sjgl = ObservableField<YhswModel.Sjgl>()

    constructor(context: Context?) : this() {
        this.mContext = context
    }

    fun itemOnClick(){
        mContext?.let {
            val adapter = DataDetailsAdapter(mContext, ItemUtils.getList(mContext, sjgl.get(),"yhsw"))
            val layoutManager = LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false)
            MaterialDialogUtil.showItemDetailsDialog(it, "详情", adapter, layoutManager).show()
        }
    }
}