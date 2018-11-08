package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
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
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.*
import com.otitan.ui.mview.ISlfh
import com.otitan.ui.mview.IXzzf
import com.otitan.ui.mview.IYhsw
import com.otitan.util.GsonUtil
import com.otitan.util.ScreenTool
import com.otitan.util.Utils
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

        viewmodel?.let {
            BarChartInit.init(binding.chartXzzf, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableXzzf, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(XzzfDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setDescription() {
        if (viewmodel?.hasData?.get() != true) {
            return
        }
        val obj = viewmodel?.data?.data?.get(0) ?: return
        val gson = GsonUtil.getIntGson()
        val json = gson.toJson(obj)
        val xzzf = gson.fromJson(json,XzzfModel::class.java)
        val s = "${viewmodel?.year}年浙江省行政处罚案件共${xzzf.Count}宗," +
                "其中森林火灾案${xzzf.SLHZA}宗,毁坏林木、苗木${xzzf.HHLM}宗," +
                "滥伐林木${xzzf.LFLM}宗,滥伐森林或者其他林木${xzzf.LFLMQT}宗,盗伐林木${xzzf.DFLM}宗," +
                "违反森林植物检疫规定${xzzf.WFSL}宗,违法征、占用林地${xzzf.WFZ}宗," +
                "违法收购、运输木材${xzzf.WFSG}宗,非法收购、出售、运输野生动物及其产品${xzzf.FFSG}宗," +
                "非法经营、加工木材${xzzf.FFJY}宗,其他${xzzf.Other}宗"
        binding.pestTvDes.text = Utils.getSpanned(s)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartXzzf, list, activity,
                BarDataSet(list, "单位:宗"))
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<XzzfModel<Any>>>()
        val name = Column<XzzfModel<Any>>("地区", "Name")
        val column0 = Column<XzzfModel<Any>>("森林火灾案", "SLHZA")
        val column1 = Column<XzzfModel<Any>>("毁坏林木、苗木", "HHLM")
        val column2 = Column<XzzfModel<Any>>("滥伐林木", "LFLM")
        val column3 = Column<XzzfModel<Any>>("滥伐森林或者其他林木", "LFLMQT")
        val column4 = Column<XzzfModel<Any>>("盗伐林木", "DFLM")
        val column5 = Column<XzzfModel<Any>>("违反森林植物检疫规定", "WFSL")
        val column6 = Column<XzzfModel<Any>>("违法征、占用林地", "WFZ")
        val column7 = Column<XzzfModel<Any>>("违法收购、运输木材", "WFSG")
        val column8 = Column<XzzfModel<Any>>("非法收购、出售、运输野生动物及其产品", "FFSG")
        val column9 = Column<XzzfModel<Any>>("非法经营、加工木材", "FFJY")
        val column10 = Column<XzzfModel<Any>>("其他", "Other")
        val column11 = Column<XzzfModel<Any>>("行政处罚宗件总数", "Count")
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