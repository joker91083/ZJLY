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
import com.bin.david.form.data.table.MapTableData
import com.bin.david.form.data.table.TableData
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.ResourceManageViewModel
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.IResourceManage
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmResourceManageBinding
import org.jetbrains.anko.collections.forEachWithIndex
import java.lang.Exception
/**
 * 资源管护
 */
class ResourceManageFragment : BaseFragment<FmResourceManageBinding, ResourceManageViewModel>(), IResourceManage {

    var viewmodel: ResourceManageViewModel? = null
    var data: ResultModel<Any>? = null
    var barDataset: BarDataSet? = null
    private var xAxis: XAxis? = null                //X轴
    private var leftYAxis: YAxis? = null            //左侧Y轴
    private var rightYaxis: YAxis? = null           //右侧Y轴
    private var legend: Legend? = null              //图例
    private var limitLine: LimitLine? = null        //限制线
    private val colorArray = arrayOf(R.color.colorPrimaryDark, R.color.ccc_red, R.color.ccc_blue, R.color.ccc_beige,
            R.color.problem, R.color.motorway, R.color.trunk, R.color.primary,
            R.color.secondary, R.color.orange, R.color.tertiary, R.color.colorPrimary)

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_resource_manage
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): ResourceManageViewModel {
        if (viewmodel == null) {
            viewmodel = ResourceManageViewModel(activity, this)
        }
        return viewmodel as ResourceManageViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarResource.title = "资源管护情况"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarResource)
        binding.toolbarResource.setNavigationOnClickListener { activity?.finish() }

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
        binding.dataTableResource.config.isShowXSequence = false
        binding.dataTableResource.config.isShowYSequence = false
    }

    private fun initBarChart() {
        val chartBar = binding.chartResource
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
        legend = binding.chartResource.legend
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
            if (binding.chartResource.data != null) {
                val count = binding.chartResource.data.dataSetCount
                binding.chartResource.data.removeDataSet(count - 1)
            }
            if (list.isNotEmpty() && activity != null) {

                barDataset = when (viewmodel?.type) {
                    1,2,3 -> BarDataSet(list, "单位:万亩")
                    4 -> BarDataSet(list, "单位:%")
                    else -> BarDataSet(list, "单位:万m³")
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
                binding.chartResource.data = barData
                binding.chartResource.setFitBars(true)
            }
            binding.chartResource.notifyDataSetChanged()
            binding.chartResource.invalidate()
        } catch (e: Exception) {
            Log.e("tag", "表格数据设置异常：$e")
        }
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<ResourceModel.LDMJModel>>()
                val name = Column<ResourceModel.LDMJModel>("地区", "Name")
                val area = Column<ResourceModel.LDMJModel>("林地总面积", "Area")
                val QMLD = Column<ResourceModel.LDMJModel>("乔木林地", "QMLD")
                val ZLD = Column<ResourceModel.LDMJModel>("竹林地", "ZLD")
                val SLD = Column<ResourceModel.LDMJModel>("疏林地", "SLD")
                val TSGMLD = Column<ResourceModel.LDMJModel>("特殊灌木林地", "TSGMLD")
                val YBGMLD = Column<ResourceModel.LDMJModel>("一般灌木林地", "YBGMLD")
                val WCLZLD = Column<ResourceModel.LDMJModel>("未成林造林地", "WCLZLD")
                val YLD = Column<ResourceModel.LDMJModel>("宜林地", "YLD")
                val other = Column<ResourceModel.LDMJModel>("其它", "Other")
                columnList.add(name)
                columnList.add(area)
                columnList.add(QMLD)
                columnList.add(ZLD)
                columnList.add(SLD)
                columnList.add(TSGMLD)
                columnList.add(YBGMLD)
                columnList.add(WCLZLD)
                columnList.add(YLD)
                columnList.add(other)

                binding.dataTableResource.tableData = TableData("浙江省及各地市林地总面积（单位:万亩）", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<ResourceModel.SLMJModel>>()
                val name = Column<ResourceModel.SLMJModel>("地区", "Name")
                val area = Column<ResourceModel.SLMJModel>("森林面积", "Area")
                columnList.add(name)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市森林总面积（单位:万亩）", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<ResourceModel.GYLMJModel>>()
                val name = Column<ResourceModel.GYLMJModel>("地区", "Name")
                val cArea = Column<ResourceModel.GYLMJModel>("国家级公益林", "CountryArea")
                val lArea = Column<ResourceModel.GYLMJModel>("地方公益林", "LocalArea")
                val area = Column<ResourceModel.GYLMJModel>("合计", "Area")
                columnList.add(name)
                columnList.add(cArea)
                columnList.add(lArea)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市公益林总面积（单位:万亩）", tableData, columnList as List<Column<Any>>)
            }
            4 -> {
                val columnList = ArrayList<Column<ResourceModel.SLFGLModel>>()
                val name = Column<ResourceModel.SLFGLModel>("地区", "Name")
                val area = Column<ResourceModel.SLFGLModel>("森林覆盖率", "Area")
                columnList.add(name)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市森林覆盖率（单位:%）", tableData, columnList as List<Column<Any>>)
            }
            else -> {
                val columnList = ArrayList<Column<ResourceModel.HLMXJModel>>()
                val name = Column<ResourceModel.HLMXJModel>("地区", "Name")
                val area = Column<ResourceModel.HLMXJModel>("活立木蓄积", "Area")
                columnList.add(name)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市活立木蓄积（单位:万m³）", tableData, columnList as List<Column<Any>>)
            }
        }


    }

}