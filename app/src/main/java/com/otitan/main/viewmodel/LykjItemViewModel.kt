package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import com.otitan.base.BaseViewModel
import com.otitan.main.adapter.DataDetailsAdapter
import com.otitan.main.utils.ItemUtils
import com.otitan.model.LykjModel
import com.otitan.util.MaterialDialogUtil

class LykjItemViewModel() : BaseViewModel() {
    //年份
    val year = ObservableField<String>()
    //项目编号
    val xmbh = ObservableField<String>()
    //项目名称
    val xmmc = ObservableField<String>()
    //承担单位
    val cddw = ObservableField<String>()
    //主持人
    val zcr = ObservableField<String>()
    //技术支撑单位
    val jszcdw = ObservableField<String>()
    //总经费
    val zjf = ObservableField<String>()
    //财政补助
    val czbz = ObservableField<String>()
    //备注
    val remark = ObservableField<String>()
    //指标类型1：省院合作项目，2：示范资金项目，3：科技标准-国家标准，4：科技标准-行业标准，5：科技标准-省级地方标准，
    // 6：科技标准-浙江制造标准
    val type = ObservableInt()
    val syhzxm = ObservableField<LykjModel.Syhzxm>()
    val sfzjxm = ObservableField<LykjModel.Sfzjxm>()
    val gjbz = ObservableField<LykjModel.Gjbz>()
    val hybz = ObservableField<LykjModel.Hybz>()
    val sjdfbz = ObservableField<LykjModel.Sjdfbz>()
    val zjzzbz = ObservableField<LykjModel.Zjzzbz>()

    constructor(context: Context?) : this() {
        mContext = context
    }

    fun itemOnClick() {
        mContext?.let {
            var itemType = "lykj"
            when (type.get()) {
                1, 2 -> {
                    itemType = "lykj_1"
                }
            }
            var obj: Any? = null
            when (type.get()) {
                1 -> obj = syhzxm.get()
                2 -> obj = sfzjxm.get()
                3 -> obj = gjbz.get()
                4 -> obj = hybz.get()
                5 -> obj = sjdfbz.get()
                6 -> obj = zjzzbz.get()
            }
            val adapter = DataDetailsAdapter(mContext, ItemUtils.getList(mContext, obj, itemType))
            val layoutManager = LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false)
            MaterialDialogUtil.showItemDetailsDialog(it, "详情", adapter, layoutManager).show()
        }
    }
}