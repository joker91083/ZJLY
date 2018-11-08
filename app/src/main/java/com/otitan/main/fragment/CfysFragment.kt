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
import com.google.gson.internal.LinkedTreeMap
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.CfysViewModel
import com.otitan.main.viewmodel.YzlViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.CfysModel
import com.otitan.model.ResultModel
import com.otitan.model.YzlModel
import com.otitan.ui.mview.ILQuan
import com.otitan.ui.mview.IYzl
import com.otitan.util.ScreenTool
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmCfysBinding
import com.otitan.zjly.databinding.FmYzlBinding

/**
 * 采伐运输
 */
class CfysFragment : BaseFragment<FmCfysBinding, CfysViewModel>(), ILQuan {

    var viewmodel: CfysViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_cfys
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): CfysViewModel {
        if (viewmodel == null) {
            viewmodel = CfysViewModel(activity, this)
        }
        return viewmodel as CfysViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbar.title = "采伐运输"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }

        binding.spinnerType.setSelection(0, true)
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.type?.set(p2 + 1)
                when (p2) {
                    0 -> {
                        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.nianfen_cfys1))
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
                        binding.spinnerYear.adapter = adapter
                    }
                    1 -> {
                        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.nianfen_cfys2))
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
                        binding.spinnerYear.adapter = adapter
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
                    it.getData(it.type.get(), year)
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
                    it.getData(p2 + 1, it.year)
                    it.type.set(p2 + 1)
                }
            }
        }

        viewmodel?.let {
            BarChartInit.init(binding.chart, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTable, context)
    }

    override fun setDescription() {
        if (viewmodel?.hasData?.get() != true) {
            return
        }
        val count = viewmodel?.total?.get("count").toString()
        val s = when (viewmodel?.type?.get()) {
            1 -> {
                when (viewmodel?.searchType) {
                    1 -> "${viewmodel?.year}年浙江省证书个数：${count.split(".")[0]}本"
                    2 -> "${viewmodel?.year}年浙江省采伐面积：${count}亩"
                    3 -> "${viewmodel?.year}年浙江省采伐蓄积：${count}立方米"
                    4 -> "${viewmodel?.year}年浙江省采伐株数：${count}株"
                    else -> ""
                }
            }
            2 -> "${viewmodel?.year}年浙江省木材运输证：${count.split(".")[0]}本"
            else -> ""
        }
        binding.pestTvDes.text = Utils.getSpanned(s)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chart, list, activity,
                when (viewmodel?.type?.get()) {
                    1 -> BarDataSet(list, "单位:本")
                    2 -> BarDataSet(list, "单位:亩")
                    3 -> BarDataSet(list, "单位:立方米")
                    4 -> BarDataSet(list, "单位:株")
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
                startContainerActivity(CfysDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<CfysModel.Xxzl>>()
        val name = Column<CfysModel.Xxzl>("地区", "name")
        var value = Column<CfysModel.Xxzl>("证书个数", "count")
        var tableName = ""
        when (viewmodel?.type?.get()) {
            1 -> {
                when (viewmodel?.searchType) {
                    1 -> {
                        tableName = "${viewmodel?.year}年浙江省及各地市证书个数(单位:本)"
                        value = Column("证书个数", "count")
                    }
                    2 -> {
                        tableName = "${viewmodel?.year}年浙江省及各地市采伐面积(单位:亩)"
                        value = Column("采伐面积", "count")
                    }
                    3 -> {
                        tableName = "${viewmodel?.year}年浙江省及各地市采伐蓄积(单位:立方米)"
                        value = Column("采伐蓄积", "count")
                    }
                    4 -> {
                        tableName = "${viewmodel?.year}年浙江省及各地市采伐株树(单位:株)"
                        value = Column("采伐株数", "count")
                    }
                }
            }
            2 -> {
                tableName = "${viewmodel?.year}年浙江省及各地市木材运输证(单位:本)"
                value = Column("证书个数", "count")
            }
        }
        columnList.add(name)
        columnList.add(value)
        binding.dataTable.tableData = TableData(tableName, tableData, columnList as List<Column<Any>>)
    }

}