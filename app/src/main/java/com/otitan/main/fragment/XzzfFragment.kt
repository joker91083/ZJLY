package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import com.otitan.main.viewmodel.SlfhViewModel
import com.otitan.main.viewmodel.XzzfViewModel
import com.otitan.main.viewmodel.YhswViewModel
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.model.SlfhModel
import com.otitan.model.YhswModel
import com.otitan.ui.mview.ISlfh
import com.otitan.ui.mview.IXzzf
import com.otitan.ui.mview.IYhsw
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmSlfhBinding
import com.otitan.zjly.databinding.FmXzzfBinding
import com.otitan.zjly.databinding.FmYhswBinding
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * 有害生物
 */
class XzzfFragment : BaseFragment<FmXzzfBinding, XzzfViewModel>(), IXzzf {

    var viewmodel: XzzfViewModel? = null
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
        return R.layout.fm_xzzf
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): XzzfViewModel {
        if (viewmodel == null) {
            viewmodel = XzzfViewModel(activity, this)
        }
        return viewmodel as XzzfViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarXzzf.title = "行政执法"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarXzzf)
        binding.toolbarXzzf.setNavigationOnClickListener { activity?.finish() }

        val array = resources.getStringArray(R.array.zhibiao_slfh)
        val list = ArrayList<String>()
        array.forEachWithIndex { i, s ->
            list.add(s.split(",")[0])
        }
        binding.spinnerYear.setSelection(0, true)
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    val year = (p0 as Spinner).getItemAtPosition(p2).toString().toInt()
                    it.getData(year)
                    it.year = year
                }
            }
        }

        initBarChart()
        //表格设置 隐藏左边数字、顶上字母
        binding.dataTableXzzf.config.isShowXSequence = false
        binding.dataTableXzzf.config.isShowYSequence = false
    }

    private fun initBarChart() {
        val chartBar = binding.chartXzzf
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
            value.toString().split("\\.")[0]
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
        legend = binding.chartXzzf.legend
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
            if (binding.chartXzzf.data != null) {
                val count = binding.chartXzzf.data.dataSetCount
                binding.chartXzzf.data.removeDataSet(count - 1)
            }
            if (list.isNotEmpty() && activity != null) {

                barDataset = BarDataSet(list, "单位:亩")
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
                binding.chartXzzf.data = barData
                binding.chartXzzf.setFitBars(true)
            }
            binding.chartXzzf.notifyDataSetChanged()
            binding.chartXzzf.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<YhswModel>>()
        val name = Column<YhswModel>("地区", "Name")
        val column0 = Column<YhswModel>("森林火灾案", "SLHZA")
        val column1 = Column<YhswModel>("毁坏林木、苗木", "HHLM")
        val column2 = Column<YhswModel>("滥伐林木", "LFLM")
        val column3 = Column<YhswModel>("滥伐森林或者其他林木", "LFLMQT")
        val column4 = Column<YhswModel>("盗伐林木", "DFLM")
        val column5 = Column<YhswModel>("违反森林植物检疫规定", "WFSL")
        val column6 = Column<YhswModel>("违法征、占用林地", "WFZ")
        val column7 = Column<YhswModel>("违法收购、运输木材", "WFSG")
        val column8 = Column<YhswModel>("非法收购、出售、运输野生动物及其产品", "FFSG")
        val column9 = Column<YhswModel>("非法经营、加工木材", "FFJY")
        val column10 = Column<YhswModel>("其他", "Other")
        val column11 = Column<YhswModel>("行政处罚宗件总数", "Count")
        columnList.add(name)
        columnList.add(column0)
        columnList.add(column1)
        columnList.add(column2)
        columnList.add(column3)
        columnList.add(column4)
        columnList.add(column5)
        columnList.add(column6)
        columnList.add(column7)
        columnList.add(column8)
        columnList.add(column9)
        columnList.add(column10)
        columnList.add(column11)
        binding.dataTableXzzf.tableData = TableData("浙江省及各地市行政处罚（单位:宗）", tableData, columnList as List<Column<Any>>)
    }

}