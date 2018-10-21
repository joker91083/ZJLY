package com.otitan.main.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
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
    //是否是科技标准 true是 false否
    val isKjbz = ObservableBoolean(false)

    fun itemOnClick(){

    }
}