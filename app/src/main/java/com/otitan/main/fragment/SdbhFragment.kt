package com.otitan.main.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.SdbhViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.SdbhModel
import com.otitan.ui.mview.IGylc
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmSdbhBinding

/**
 * 湿地保护
 */
class SdbhFragment : BaseFragment<FmSdbhBinding, SdbhViewModel>(), IGylc {

    var viewmodel: SdbhViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_sdbh
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): SdbhViewModel {
        if (viewmodel == null) {
            viewmodel = SdbhViewModel(activity, this)
        }
        return viewmodel as SdbhViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarSdbh.title = "湿地保护"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarSdbh)
        binding.toolbarSdbh.setNavigationOnClickListener { activity?.finish() }

        viewmodel?.let {
            BarChartInit.init(binding.chartSdbh, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableSdbh, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(SdbhDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setDescription() {
        if (viewmodel?.hasData?.get() != true) {
            return
        }
        val obj = viewmodel?.data?.data?.get(0) ?: return
        val s = "浙江省湿地总面积共:${obj["AllArea"]}公顷,湿地面积${obj["WetArea"]}公顷," +
                "自然湿地面积${obj["NaturalWetArea"]}公顷"
        binding.pestTvDes.text = Utils.getSpanned(s)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartSdbh, list, activity,
                BarDataSet(list, "单位:公顷"))
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<SdbhModel<Any>>>()
        val name = Column<SdbhModel<Any>>("地区", "Name")
        val column1 = Column<SdbhModel<Any>>("总面积", "AllArea")
        val column2 = Column<SdbhModel<Any>>("湿地面积", "WetArea")
        val column3 = Column<SdbhModel<Any>>("自然湿地面积", "NaturalWetArea")
        val column4 = Column<SdbhModel<Any>>("总个数", "Count")
        columnList.add(name)
        columnList.add(column1)
        columnList.add(column2)
        columnList.add(column3)
        columnList.add(column4)
        binding.dataTableSdbh.tableData = TableData("浙江省及各地市湿地（单位:公顷）", tableData, columnList as List<Column<Any>>)
    }

}