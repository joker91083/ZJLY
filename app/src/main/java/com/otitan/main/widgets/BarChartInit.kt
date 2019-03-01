package com.otitan.main.widgets

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.Log
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import com.otitan.zjly.R
import java.lang.Exception

class BarChartInit {
    companion object {
        val colorArray = arrayOf(R.color.colorPrimaryDark, R.color.ccc_red, R.color.ccc_blue, R.color.ccc_beige,
                R.color.problem, R.color.motorway, R.color.trunk, R.color.primary,
                R.color.secondary, R.color.orange, R.color.tertiary, R.color.colorPrimary)

        @JvmStatic
        fun init(barChart: BarChart, xValue: List<String>) {
            // 没有数据的时候显示
            barChart.setNoDataText("暂无数据")
            //是否显示边界
            barChart.setDrawBorders(false)
            //是否显示网格线
            barChart.setDrawGridBackground(false)
            //是否可以拖动
            barChart.isDragEnabled = true
            //是否有触摸事件
            barChart.setTouchEnabled(true)
            //xy轴动画事件
            barChart.animateXY(1500, 1500)
            //右下角说明设置
            val desc = Description()
            desc.text = ""
            barChart.description = desc

            /***XY轴的设置***/
            val xAxis = barChart.xAxis
            val xAxisFormat = IAxisValueFormatter { value, axis ->
                when {
                    xValue.isEmpty() -> value.toString()
                    value == -1f || value > xValue.size - 1 -> ""
                    else -> xValue[value.toInt()]
                }
            }
            val yAxisFormat = IAxisValueFormatter { value, axis ->
                if (value.compareTo(value.toLong()) == 0) {
                    val l = value.toLong()
                    l.toString()
                } else {
                    value.toString()
                }
            }
            val leftYAxis = barChart.axisLeft
            //        rightYaxis = barChart.axisRight
            barChart.axisRight.isEnabled = false
            xAxis?.valueFormatter = xAxisFormat
            barChart.axisLeft.valueFormatter = yAxisFormat
            barChart.axisRight.valueFormatter = yAxisFormat
            xAxis?.labelRotationAngle = -60f
            barChart.extraBottomOffset = -35f
            //        barChart.extraLeftOffset = 10f
            //        barChart.offsetLeftAndRight(10)
            //X轴设置显示位置在底部
            xAxis?.position = XAxis.XAxisPosition.BOTTOM
            //        xAxis?.axisMinimum = 0f
            xAxis?.granularity = 1f
            //保证Y轴从0开始，不然会上移一点
            leftYAxis?.axisMinimum = 0f
//        rightYaxis.axisMinimum = 0f


            /***图例 标签 设置***/
            val legend = barChart.legend
            //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
            legend?.form = Legend.LegendForm.SQUARE
            legend?.textSize = 12f
            legend?.formToTextSpace = 4f
            //是否绘制在图表里面
            legend?.setDrawInside(false)
            //显示位置 左下方
            legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend?.orientation = Legend.LegendOrientation.HORIZONTAL
            //调整图例到x/y轴的距离
            //legend?.yEntrySpace = 8f
            legend?.xEntrySpace = 1f
        }

        fun setBarChartData(barChart: BarChart, list: ArrayList<BarEntry>, activity: Activity?,
                            barDataset: BarDataSet?) {
            try {
                if (barChart.data != null) {
                    val count = barChart.data.dataSetCount
                    barChart.data.removeDataSet(count - 1)
                }
                if (list.isNotEmpty() && activity != null) {
                    val colors = ArrayList<Int>()
                    for (i in 0 until colorArray.size) {
                        colors.add(ContextCompat.getColor(activity, colorArray[i]))
                    }
                    barDataset?.colors = colors
                    val dataSets = ArrayList<IBarDataSet>()
                    dataSets.add(barDataset!!)
                    val barData = BarData(dataSets)
                    barData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
                        if (value.compareTo(value.toLong()) == 0) {
                            val l = value.toLong()
                            l.toString()
                        } else {
                            value.toString()
                        }
                    }
                    barData.barWidth = 0.9f
                    barData.setValueTextSize(10f)
                    barChart.data = barData
                    barChart.setFitBars(true)
                }
                barChart.notifyDataSetChanged()
                barChart.invalidate()
            } catch (e: Exception) {
                Log.e("tag", "表格数据设置异常：$e")
            }
        }
    }
}