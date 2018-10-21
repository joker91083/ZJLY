package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.YzlViewModel
import com.otitan.model.ResultModel
import com.otitan.model.SlfhModel
import com.otitan.model.YzlModel
import com.otitan.ui.mview.IYzl
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmYzlBinding

/**
 * 营造林
 */
class YzlFragment : BaseFragment<FmYzlBinding, YzlViewModel>(), IYzl {

    var viewmodel: YzlViewModel? = null
    var data: ResultModel<Any>? = null
    var barDataset: BarDataSet? = null
    private var xAxis: XAxis? = null                //X轴
    private var leftYAxis: YAxis? = null            //左侧Y轴
    private var rightYaxis: YAxis? = null           //右侧Y轴
    private var legend: Legend? = null              //图例
    private val colorArray = arrayOf(R.color.colorPrimaryDark, R.color.ccc_red, R.color.ccc_blue, R.color.ccc_beige,
            R.color.problem, R.color.motorway, R.color.trunk, R.color.primary,
            R.color.secondary, R.color.orange, R.color.tertiary, R.color.colorPrimary)
    private val indexArray = arrayOf(R.array.zhibiao_yzl_wcqk, R.array.zhibiao_yzl_xdjh)

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_yzl
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): YzlViewModel {
        if (viewmodel == null) {
            viewmodel = YzlViewModel(activity, this)
        }
        return viewmodel as YzlViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarYzl.title = "营造林"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarYzl)
        binding.toolbarYzl.setNavigationOnClickListener { activity?.finish() }

//        val array = resources.getStringArray(indexArray[0])
//        val list = ArrayList<String>()
//        array.forEachWithIndex { i, s ->
//            list.add(s.split(",")[0])
//        }
        binding.spinnerType.setSelection(0, true)
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.zhibiao_yzl_wcqk))
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerIndex.adapter = adapter
                    }
                    1 -> {
                        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.zhibiao_yzl_xdjh))
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerIndex.adapter = adapter
                    }
                }
            }
        }
//        val yearAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.nianfen_resource))
//        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerYear.adapter = yearAdapter
        binding.spinnerYear.setSelection(0, true)
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    val year = (p0 as Spinner).getItemAtPosition(p2).toString().toInt()
                    it.getData(it.type, year)
                    it.year = year
                }
            }
        }

        binding.spinnerIndex.setSelection(0, true)
        binding.spinnerIndex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    //val type = (p0 as Spinner).getItemAtPosition(p2).toString()
                    if (binding.spinnerType.selectedItem.toString() == "完成情况") {
                        it.getData(p2 + 1, it.year)
                        it.type = p2 + 1
                    } else if (binding.spinnerType.selectedItem.toString() == "下达计划") {
                        it.getData(p2 + 9, it.year)
                        it.type = p2 + 9
                    }

                }
            }
        }

        initBarChart()
        //表格设置 隐藏左边数字、顶上字母
        binding.dataTableYzl.config.isShowXSequence = false
        binding.dataTableYzl.config.isShowYSequence = false
    }

    private fun initBarChart() {
        val chartBar = binding.chartYzl
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
        chartBar.extraBottomOffset = -35f
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
        legend = binding.chartYzl.legend
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
        legend?.xEntrySpace = 3f
    }

    fun getList(index: Int) {

    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        try {
//            initBarChart()
//            if (binding.chartResource.data != null && binding.chartResource.data.dataSetCount > 0 && list.isNotEmpty()) {
//                val count = binding.chartResource.data.dataSetCount
//                val entry = binding.chartResource.barData.getDataSetByIndex(0).getEntryForIndex(count-1)
//                binding.chartResource.data.removeDataSet(count-1)
//////                binding.chartResource.data.get
////                barDataset
//                barDataset?.values = list
//
//                binding.chartResource.notifyDataSetChanged()
//                binding.chartResource.invalidate()
//            } else
            if (binding.chartYzl.data != null) {
                val count = binding.chartYzl.data.dataSetCount
                binding.chartYzl.data.removeDataSet(count - 1)
            }
            if (list.isNotEmpty() && activity != null) {

                barDataset = when (viewmodel?.type) {
                    1, 2, 3, 4, 5, 6, 8 -> BarDataSet(list, "单位:公顷")
                    9, 10, 11, 12, 13, 14 -> BarDataSet(list, "单位:亩")
                    7, 15 -> BarDataSet(list, "单位:万株")
                    else -> BarDataSet(list, "")
                }

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
                binding.chartYzl.data = barData
                binding.chartYzl.setFitBars(true)
            }
            binding.chartYzl.notifyDataSetChanged()
            binding.chartYzl.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<YzlModel>>()
        val name = Column<YzlModel>("地区", "name")
        columnList.add(name)
        when (viewmodel?.type) {
            1 -> {
                val value = Column<YzlModel>("造林面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市造林面积单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val value = Column<YzlModel>("人工造林面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市人工造林面积单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val value = Column<YzlModel>("迹地更新面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市迹地更新面积单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            4 -> {
                val value = Column<YzlModel>("森林抚育", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市森林抚育单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            5 -> {
                val value = Column<YzlModel>("年末实有封山(沙)育林面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市年末实有封山(沙)育林面积单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            6 -> {
                val value = Column<YzlModel>("四旁(零星)植树", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市四旁(零星)植树单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            7 -> {
                val value = Column<YzlModel>("当年苗木产量", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市当年苗木产量单位:万株", tableData, columnList as List<Column<Any>>)
            }
            8 -> {
                val value = Column<YzlModel>("育苗面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市育苗面积单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            9 -> {
                val value = Column<YzlModel>("造林面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市造林面积单位:亩", tableData, columnList as List<Column<Any>>)
            }
            10 -> {
                val value = Column<YzlModel>("人工造林面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市人工造林面积单位:亩", tableData, columnList as List<Column<Any>>)
            }
            11 -> {
                val value = Column<YzlModel>("封山育林", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市封山育林单位:亩", tableData, columnList as List<Column<Any>>)
            }
            12 -> {
                val value = Column<YzlModel>("森林抚育", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市森林抚育单位:亩", tableData, columnList as List<Column<Any>>)
            }
            13 -> {
                val value = Column<YzlModel>("低效林改造", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市低效林改造单位:亩", tableData, columnList as List<Column<Any>>)
            }
            14 -> {
                val value = Column<YzlModel>("当年新育苗面积", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市当年新育苗面积单位:亩", tableData, columnList as List<Column<Any>>)
            }
            15 -> {
                val value = Column<YzlModel>("四旁树", "value")
                columnList.add(value)
                binding.dataTableYzl.tableData = TableData("浙江省及各地市四旁树单位:万株", tableData, columnList as List<Column<Any>>)
            }
        }


    }

}