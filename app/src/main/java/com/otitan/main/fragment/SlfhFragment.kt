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
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.model.SlfhModel
import com.otitan.ui.mview.ISlfh
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmSlfhBinding
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * 森林防火
 */
class SlfhFragment : BaseFragment<FmSlfhBinding, SlfhViewModel>(), ISlfh {

    var viewmodel: SlfhViewModel? = null
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
        return R.layout.fm_slfh
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): SlfhViewModel {
        if (viewmodel == null) {
            viewmodel = SlfhViewModel(activity, this)
        }
        return viewmodel as SlfhViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarSlfh.title = "森林防火"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarSlfh)
        binding.toolbarSlfh.setNavigationOnClickListener { activity?.finish() }

        val array = resources.getStringArray(R.array.zhibiao_slfh)
        val list = ArrayList<String>()
        array.forEachWithIndex { i, s ->
            list.add(s.split(",")[0])
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

//        val typeAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, list)
//        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerType.adapter = typeAdapter
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
        binding.dataTableSlfh.config.isShowXSequence = false
        binding.dataTableSlfh.config.isShowYSequence = false
    }

    private fun initBarChart() {
        val chartBar = binding.chartSlfh
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
        legend = binding.chartSlfh.legend
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
        legend?.xEntrySpace = 4f
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
            if (binding.chartSlfh.data != null) {
                val count = binding.chartSlfh.data.dataSetCount
                binding.chartSlfh.data.removeDataSet(count - 1)
            }
            if (list.isNotEmpty() && activity != null) {

                barDataset = when (viewmodel?.type) {
                    1 -> BarDataSet(list, "单位:次")
                    2, 3 -> BarDataSet(list, "单位:亩")
                    4 -> BarDataSet(list, "单位:万元")
                    5 -> BarDataSet(list, "单位:人")
                    else -> BarDataSet(list, "单位:m³/万株")
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
                binding.chartSlfh.data = barData
                binding.chartSlfh.setFitBars(true)
            }
            binding.chartSlfh.notifyDataSetChanged()
            binding.chartSlfh.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<SlfhModel.Hzcs>>()
                val name = Column<SlfhModel.Hzcs>("地区", "Name")
                val hztj = Column<SlfhModel.Hzcs>("火灾合计", "TotalFireCount")
                val ybhz = Column<SlfhModel.Hzcs>("一般火灾", "NormalFire")
                val jdhz = Column<SlfhModel.Hzcs>("较大火灾", "LargeFire")
                val zdhz = Column<SlfhModel.Hzcs>("重大火灾", "MajorFire")
                val tdhz = Column<SlfhModel.Hzcs>("特大火灾", "OversizeFire")
                columnList.add(name)
                columnList.add(hztj)
                columnList.add(ybhz)
                columnList.add(jdhz)
                columnList.add(zdhz)
                columnList.add(tdhz)

                binding.dataTableSlfh.tableData = TableData("浙江省及各地市火灾次数（单位:次）", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<SlfhModel.Hcmj>>()
                val name = Column<SlfhModel.Hcmj>("地区", "Name")
                val hcmj = Column<SlfhModel.Hcmj>("火场面积", "TotalFireCount")
                columnList.add(name)
                columnList.add(hcmj)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市火场面积（单位:亩）", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<SlfhModel.Ssmj>>()
                val name = Column<SlfhModel.Ssmj>("地区", "Name")
                val ssmj = Column<SlfhModel.Ssmj>("损失面积", "TotalFireCount")
                columnList.add(name)
                columnList.add(ssmj)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市损失面积（单位:亩）", tableData, columnList as List<Column<Any>>)
            }
            4 -> {
                val columnList = ArrayList<Column<SlfhModel.Phjf>>()
                val name = Column<SlfhModel.Phjf>("地区", "Name")
                val phjf = Column<SlfhModel.Phjf>("扑火经费", "TotalFireCount")
                columnList.add(name)
                columnList.add(phjf)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市扑火经费（单位:万元）", tableData, columnList as List<Column<Any>>)
            }
            5 -> {
                val columnList = ArrayList<Column<SlfhModel.Rysw>>()
                val name = Column<SlfhModel.Rysw>("地区", "Name")
                val totalCount = Column<SlfhModel.Rysw>("合计", "TotalCount")
                val minor = Column<SlfhModel.Rysw>("轻伤", "Minor")
                val serious = Column<SlfhModel.Rysw>("重伤", "Serious")
                val death = Column<SlfhModel.Rysw>("死亡", "Death")
                columnList.add(name)
                columnList.add(totalCount)
                columnList.add(minor)
                columnList.add(serious)
                columnList.add(death)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市火灾人员伤亡（单位:人）", tableData, columnList as List<Column<Any>>)
            }
            else -> {
                val columnList = ArrayList<Column<SlfhModel.Sslm>>()
                val name = Column<SlfhModel.Sslm>("地区", "Name")
                val accumulation = Column<SlfhModel.Sslm>("成林蓄积(m³)", "Accumulation")
                val seedling = Column<SlfhModel.Sslm>("幼苗株数(万株)", "Seedling")
                columnList.add(name)
                columnList.add(accumulation)
                columnList.add(seedling)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市林木损失", tableData, columnList as List<Column<Any>>)
            }
        }


    }

}