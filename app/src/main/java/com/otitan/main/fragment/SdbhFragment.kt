package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.GylcViewModel
import com.otitan.main.viewmodel.SdbhViewModel
import com.otitan.model.GylcModel
import com.otitan.model.ResultModel
import com.otitan.model.SdbhModel
import com.otitan.ui.mview.IGylc
import com.otitan.ui.mview.ISlgy
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmGylcBinding
import com.otitan.zjly.databinding.FmSdbhBinding

/**
 * 湿地保护
 */
class SdbhFragment : BaseFragment<FmSdbhBinding, SdbhViewModel>(), IGylc {

    var viewmodel: SdbhViewModel? = null
    var data: ResultModel<Any>? = null
    var barDataset: BarDataSet? = null
    private var xAxis: XAxis? = null                //X轴
    private var leftYAxis: YAxis? = null            //左侧Y轴
    private var rightYaxis: YAxis? = null           //右侧Y轴
    private var legend: Legend? = null              //图例
    private val colorArray = arrayOf(R.color.colorPrimaryDark, R.color.ccc_red, R.color.ccc_blue, R.color.ccc_beige,
            R.color.problem, R.color.motorway, R.color.trunk, R.color.primary,
            R.color.secondary, R.color.orange, R.color.tertiary, R.color.colorPrimary)

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_sdbh
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): SdbhViewModel {
        if (viewmodel == null) {
            viewmodel = SdbhViewModel(activity, this)
        }
        return viewmodel as SdbhViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarSdbh.title = "湿地保护"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarSdbh)
        binding.toolbarSdbh.setNavigationOnClickListener { activity?.finish() }

        initBarChart()
        //表格设置 隐藏左边数字、顶上字母
        binding.dataTableSdbh.config.isShowXSequence = false
        binding.dataTableSdbh.config.isShowYSequence = false
//        val titleStyle = FontStyle()
//        titleStyle.textColor = Color.BLACK
//        titleStyle.setTextSpSize(activity, 16)
//        binding.dataTableSdbh.config.tableTitleStyle = titleStyle
    }

    private fun initBarChart() {
        val chartBar = binding.chartSdbh
        // 没有数据的时候显示
        chartBar.setNoDataText("暂无数据")
        //是否显示边界
        chartBar.setDrawBorders(false)
        //是否显示网格线
        chartBar.setDrawGridBackground(false)
        //是否可以拖动
        chartBar.isDragEnabled = true
        //是否有触摸事件
        chartBar.setTouchEnabled(true)
        //xy轴动画事件
        chartBar.animateXY(1500, 1500)
        //右下角说明设置
        val desc = Description()
        desc.text = ""
        chartBar.description = desc

        /***XY轴的设置***/
        xAxis = chartBar.xAxis
        val xAxisFormat = IAxisValueFormatter { value, axis ->
            when {
                viewmodel?.dqList?.isEmpty()!! -> value.toString()
                value == -1f || value > viewmodel?.dqList?.size!! - 1 -> ""
                else -> viewmodel?.dqList?.get(value.toInt())
            }
        }
        val yAxisFormat = IAxisValueFormatter { value, axis ->
            value.toString()
        }
        leftYAxis = chartBar.axisLeft
        //        rightYaxis = chartBar.axisRight
        chartBar.axisRight.isEnabled = false
        xAxis?.valueFormatter = xAxisFormat
        chartBar.axisLeft.valueFormatter = yAxisFormat
        chartBar.axisRight.valueFormatter = yAxisFormat
        xAxis?.labelRotationAngle = -60f
//        chartBar.extraBottomOffset = -35f
        //        chartBar.extraLeftOffset = 10f
        //        chartBar.offsetLeftAndRight(10)
        //X轴设置显示位置在底部
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        //        xAxis?.axisMinimum = 0f
        xAxis?.granularity = 1f
        //保证Y轴从0开始，不然会上移一点
        leftYAxis?.axisMinimum = 0f
        rightYaxis?.axisMinimum = 0f


        /***图例 标签 设置***/
        legend = binding.chartSdbh.legend
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
        //        legend?.yEntrySpace = 8f
        legend?.xEntrySpace = 5f
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        try {
            if (binding.chartSdbh.data != null) {
                val count = binding.chartSdbh.data.dataSetCount
                binding.chartSdbh.data.removeDataSet(count - 1)
            }
            if (list.isNotEmpty() && activity != null) {

                barDataset = BarDataSet(list, "单位:公顷")
                val colors = ArrayList<Int>()
                for (i in 0 until colorArray.size) {
                    colors.add(ContextCompat.getColor(activity!!, colorArray[i]))
                }
                barDataset?.colors = colors
                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(barDataset!!)
                val barData = BarData(dataSets)
                barData.barWidth = 0.9f
                barData.setValueTextSize(10f)
                binding.chartSdbh.data = barData
                binding.chartSdbh.setFitBars(true)
            }
            binding.chartSdbh.notifyDataSetChanged()
            binding.chartSdbh.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<SdbhModel>>()
        val name = Column<SdbhModel>("地区", "Name")
        val column1 = Column<SdbhModel>("总面积", "AllArea")
        val column2 = Column<SdbhModel>("湿地面积", "WetArea")
        val column3 = Column<SdbhModel>("自然湿地面积", "NaturalWetArea")
        val column4 = Column<SdbhModel>("总个数", "Count")
        columnList.add(name)
        columnList.add(column1)
        columnList.add(column2)
        columnList.add(column3)
        columnList.add(column4)
        binding.dataTableSdbh.tableData = TableData("浙江省及各地市湿地（单位:公顷）", tableData, columnList as List<Column<Any>>)
    }

}