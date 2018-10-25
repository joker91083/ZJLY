package com.otitan.main.viewmodel

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.otitan.base.BaseViewModel

class LykjItemViewModel : BaseViewModel() {
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

    fun itemOnClick(){

    }
}