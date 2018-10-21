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
import com.otitan.main.viewmodel.LdzzViewModel
import com.otitan.model.*
import com.otitan.ui.mview.ILdzz
import com.otitan.ui.mview.IYhsw
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLdzzBinding

/**
 * 林地征占
 */
class LdzzFragment : BaseFragment<FmLdzzBinding, LdzzViewModel>(), ILdzz {

    var viewmodel: LdzzViewModel? = null
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
        return R.layout.fm_ldzz
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): LdzzViewModel {
        if (viewmodel == null) {
            viewmodel = LdzzViewModel(activity, this)
        }
        return viewmodel as LdzzViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarLdzz.title = "林地征占"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarLdzz)
        binding.toolbarLdzz.setNavigationOnClickListener { activity?.finish() }

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
                    it.getData(p2 + 1, it.year)
                    it.type = p2 + 1
                }
            }
        }

        initBarChart()
        //表格设置 隐藏左边数字、顶上字母
        binding.dataTableLdzz.config.isShowXSequence = false
        binding.dataTableLdzz.config.isShowYSequence = false
    }

    private fun initBarChart() {
        val chartBar = binding.chartLdzz
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
        legend = binding.chartLdzz.legend
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
            if (binding.chartLdzz.data != null) {
                val count = binding.chartLdzz.data.dataSetCount
                binding.chartLdzz.data.removeDataSet(count - 1)
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
                binding.chartLdzz.data = barData
                binding.chartLdzz.setFitBars(true)
            }
            binding.chartLdzz.notifyDataSetChanged()
            binding.chartLdzz.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<LdzzModel.Xmgs>>()
                val name = Column<LdzzModel.Xmgs>("地区", "Name")
                val count = Column<LdzzModel.Xmgs>("项目个数", "Count")
                columnList.add(name)
                columnList.add(count)
                binding.dataTableLdzz.tableData = TableData("浙江省及各区县项目个数", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<LdzzModel.Zzyldmj>>()
                val name = Column<LdzzModel.Zzyldmj>("地区", "Name")
                val column1 = Column<LdzzModel.Zzyldmj>("征占用地总面积", "Total")
                val column2 = Column<LdzzModel.Zzyldmj>("防护林", "FHL")
                val column3 = Column<LdzzModel.Zzyldmj>("特用林", "TYL")
                val column4 = Column<LdzzModel.Zzyldmj>("用材林", "YCL")
                val column5 = Column<LdzzModel.Zzyldmj>("经济林", "JJL")
                val column6 = Column<LdzzModel.Zzyldmj>("薪炭林", "XTL")
                val column7 = Column<LdzzModel.Zzyldmj>("用材(经济,薪炭)林采伐迹地", "YCLCFJD")
                val column8 = Column<LdzzModel.Zzyldmj>("苗圃地", "MPD")
                val column9 = Column<LdzzModel.Zzyldmj>("未成林造地", "WCLZD")
                val column10 = Column<LdzzModel.Zzyldmj>("疏林地,灌木林地", "SLD")
                val column11 = Column<LdzzModel.Zzyldmj>("其他", "QT")
                columnList.add(name)
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
                binding.dataTableLdzz.tableData = TableData("浙江省及各区县征占用地面积 单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<LdzzModel.Ldde>>()
                val name = Column<LdzzModel.Ldde>("地区", "Name")
                val count = Column<LdzzModel.Ldde>("林地定额", "Count")
                columnList.add(name)
                columnList.add(count)
                binding.dataTableLdzz.tableData = TableData("浙江省及各区县林地定额", tableData, columnList as List<Column<Any>>)
            }
        }
    }

}