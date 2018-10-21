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
import com.otitan.main.viewmodel.LycyViewModel
import com.otitan.model.LycyModel
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.ILycy
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLycyBinding
import java.lang.Exception

/**
 * 林业产业
 */
class LycyFragment : BaseFragment<FmLycyBinding, LycyViewModel>(), ILycy {

    var viewmodel: LycyViewModel? = null
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
        return R.layout.fm_lycy
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): LycyViewModel {
        if (viewmodel == null) {
            viewmodel = LycyViewModel(activity, this)
        }
        return viewmodel as LycyViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarLycy.title = "林业产业"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarLycy)
        binding.toolbarLycy.setNavigationOnClickListener { activity?.finish() }

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

        binding.spinnerType.setSelection(0, true)
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    //val type = (p0 as Spinner).getItemAtPosition(p2).toString()
                    it.getData(p2 + 1, it.year)
                    it.type = p2 + 1
                }
            }
        }

        initBarChart()
        //表格设置 隐藏左边数字、顶上字母
        binding.dataTableLycy.config.isShowXSequence = false
        binding.dataTableLycy.config.isShowYSequence = false
    }

    private fun initBarChart() {
        val chartBar = binding.chartLycy
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
                viewmodel?.keyList?.isEmpty()!! -> value.toString()
                value == -1f || value > viewmodel?.keyList?.size!! - 1 -> ""
                else -> viewmodel?.keyList?.get(value.toInt())
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
        legend = binding.chartLycy.legend
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
        legend?.xEntrySpace = 1f
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        try {
            if (binding.chartLycy.data != null) {
                val count = binding.chartLycy.data.dataSetCount
                binding.chartLycy.data.removeDataSet(count - 1)
            }
            if (list.isNotEmpty() && activity != null) {

                barDataset = BarDataSet(list, "单位:亿元")

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
                binding.chartLycy.data = barData
                binding.chartLycy.setFitBars(true)
            }
            binding.chartLycy.notifyDataSetChanged()
            binding.chartLycy.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<LycyModel.Zcy>>()
                val name = Column<LycyModel.Zcy>("地区", "Name")
                val dycy = Column<LycyModel.Zcy>("第一产业", "First")
                val decy = Column<LycyModel.Zcy>("第二产业 ", "Second")
                val dscy = Column<LycyModel.Zcy>("第三产业", "Third")
                columnList.add(name)
                columnList.add(dycy)
                columnList.add(decy)
                columnList.add(dscy)

                binding.dataTableLycy.tableData = TableData("浙江省各地市总产值（单位：亿元）", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<LycyModel.Dycy>>()
                val name = Column<LycyModel.Dycy>("地区", "Name")
                val column1 = Column<LycyModel.Dycy>("林木育种和育苗", "lmyzym")
                val column2 = Column<LycyModel.Dycy>("营造林", "yzl")
                val column3 = Column<LycyModel.Dycy>("木材和竹材采运", "mczccy")
                val column4 = Column<LycyModel.Dycy>("经济林产品的种植与采集", "jjlcpzzcj")
                val column5 = Column<LycyModel.Dycy>("花卉及其他观赏植物种植", "hhjqtgszwzz")
                val column6 = Column<LycyModel.Dycy>("陆生野生动物繁育与利用", "lsysdwfyly")
                val column7 = Column<LycyModel.Dycy>("其他", "qt")
                columnList.add(name)
                columnList.add(column1)
                columnList.add(column2)
                columnList.add(column3)
                columnList.add(column4)
                columnList.add(column5)
                columnList.add(column6)
                columnList.add(column7)
                binding.dataTableLycy.tableData = TableData("浙江省各地市总产值（单位：亿元）", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<LycyModel.Decy>>()
                val name = Column<LycyModel.Decy>("地区", "Name")
                val column1 = Column<LycyModel.Decy>("木质工艺品和木质文教体育用品制造", "mztzwzpzz")
                val column2 = Column<LycyModel.Decy>("木竹藤家具制造", "mztjjzz")
                val column3 = Column<LycyModel.Decy>("木竹苇浆造纸和纸制品", "mzwjzzzp")
                val column4 = Column<LycyModel.Decy>("林产化学产品制造", "lchxcpzz")
                val column5 = Column<LycyModel.Decy>("木材加工和木竹藤棕苇制品制造", "mzgypmzwjtyypzz")
                val column6 = Column<LycyModel.Decy>("非木质林产品加工制造业", "fmzlcpjgzzy")
                val column7 = Column<LycyModel.Decy>("其他", "qt")
                columnList.add(name)
                columnList.add(column1)
                columnList.add(column2)
                columnList.add(column3)
                columnList.add(column4)
                columnList.add(column5)
                columnList.add(column6)
                columnList.add(column7)
                binding.dataTableLycy.tableData = TableData("浙江省各地市总产值（单位：亿元）", tableData, columnList as List<Column<Any>>)
            }
            4 -> {
                val columnList = ArrayList<Column<LycyModel.Dscy>>()
                val name = Column<LycyModel.Dscy>("地区", "Name")
                val column1 = Column<LycyModel.Dscy>("林业生产服务", "lyscfw")
                val column2 = Column<LycyModel.Dscy>("林业旅游与休闲服务", "lylyyxxfw")
                val column3 = Column<LycyModel.Dscy>("林业生态服务", "lystfw")
                val column4 = Column<LycyModel.Dscy>("林业专业技术服务", "lyzyjsfw")
                val column5 = Column<LycyModel.Dscy>("林业公共管理及其他组织服务", "lygggljqtzzfw")
                val column6 = Column<LycyModel.Dscy>("其他", "qt")
                columnList.add(name)
                columnList.add(column1)
                columnList.add(column2)
                columnList.add(column3)
                columnList.add(column4)
                columnList.add(column5)
                columnList.add(column6)
                binding.dataTableLycy.tableData = TableData("浙江省各地市总产值（单位：亿元）", tableData, columnList as List<Column<Any>>)
            }
        }


    }

}