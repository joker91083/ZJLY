package com.otitan.ui.mview

import com.github.mikephil.charting.data.BarEntry

/**
 * 信息总揽公共接口
 */
interface IInfoBase{
    fun setBarChartData(list: ArrayList<BarEntry>)

    fun setTableData(tableData: List<Any>)

    fun setDescription()
}