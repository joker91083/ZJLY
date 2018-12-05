package com.otitan.main.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AdapterView
import android.widget.Spinner
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.otitan.TitanApplication
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.YhswViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.YhswModel
import com.otitan.ui.mview.IYhsw
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmYhswBinding

/**
 * 有害生物
 */
class YhswFragment : BaseFragment<FmYhswBinding, YhswViewModel>(), IYhsw {

    var viewmodel: YhswViewModel? = null
    var typeName = "病害总计"

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_yhsw
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): YhswViewModel {
        if (viewmodel == null) {
            viewmodel = YhswViewModel(activity, this)
        }
        return viewmodel as YhswViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarYhsw.title = "有害生物"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarYhsw)
        binding.toolbarYhsw.setNavigationOnClickListener { activity?.finish() }

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
                    typeName = (p0 as Spinner).getItemAtPosition(p2).toString()
                    it.getData(p2 + 1, it.year)
                    it.type = p2 + 1
                }
            }
        }

        viewmodel?.let {
            BarChartInit.init(binding.chartYhsw, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableYhsw, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                val menu = TitanApplication.loginResult?.menu
                if (Utils.checkPermission(activity, menu?.APP_LQGL_YHSW_SJCX)) {
                    startContainerActivity(YhswDataFragment::class.java.canonicalName)
                }
            }
        }
        return true
    }

    override fun setDescription() {
        if (viewmodel?.hasData?.get() != true) {
            return
        }
        val obj = viewmodel?.data?.data?.get(0) ?: return
        val s = "${viewmodel?.year}年浙江省${typeName}其中:发生面积:${obj["HappenArea"]}亩," +
                "防治面积:${obj["PreventionArea"]}亩,防治作业面积:${obj["PreventionControlArea"]}亩," +
                "成灾面积:${obj["DisasterArea"]}亩,防治费用:${obj["Money"]}元"
        binding.pestTvDes.text = Utils.getSpanned(s)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartYhsw, list, activity,
                BarDataSet(list, "单位:亩"))
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<YhswModel<Any>>>()
        val name = Column<YhswModel<Any>>("地区", "AdminstrativeName")
        val hztj = Column<YhswModel<Any>>("发生面积", "HappenArea")
        val ybhz = Column<YhswModel<Any>>("防治面积", "PreventionArea")
        val jdhz = Column<YhswModel<Any>>("防治作业面积", "PreventionControlArea")
        val zdhz = Column<YhswModel<Any>>("成灾面积", "DisasterArea")
        val tdhz = Column<YhswModel<Any>>("防治费用", "Money")
        columnList.add(name)
        columnList.add(hztj)
        columnList.add(ybhz)
        columnList.add(jdhz)
        columnList.add(zdhz)
        columnList.add(tdhz)
        when (viewmodel?.type) {
            1 -> {
                binding.dataTableYhsw.tableData = TableData("浙江省及各地市病害总计（单位:亩、元）", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                binding.dataTableYhsw.tableData = TableData("浙江省及各地市虫害总计（单位:亩、元）", tableData, columnList as List<Column<Any>>)
            }
        }
    }

}