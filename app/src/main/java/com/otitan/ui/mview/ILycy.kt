package com.otitan.ui.mview

import com.github.mikephil.charting.data.BarEntry
import com.otitan.model.ResourceModel

interface ILycy {
    fun setBarChartData(list: ArrayList<BarEntry>)

    fun setTableData(tableData: List<Any>)
}