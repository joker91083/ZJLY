package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import com.otitan.main.viewmodel.YzlViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.ResultModel
import com.otitan.model.YzlModel
import com.otitan.ui.mview.IYzl
import com.otitan.util.ScreenTool
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmYzlBinding

/**
 * 营造林
 */
class YzlFragment : BaseFragment<FmYzlBinding, YzlViewModel>(), IYzl {

    var viewmodel: YzlViewModel? = null

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

        viewmodel?.let {
            BarChartInit.init(binding.chartYzl, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableYzl, context)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartYzl, list, activity,
                when (viewmodel?.type) {
                    1, 2, 3, 4, 5, 6, 8 -> BarDataSet(list, "单位:公顷")
                    9, 10, 11, 12, 13, 14 -> BarDataSet(list, "单位:亩")
                    7, 15 -> BarDataSet(list, "单位:万株")
                    else -> BarDataSet(list, "")
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(YzlDataFragment::class.java.canonicalName)
            }
        }
        return true
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