package com.otitan.main.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.SlgyViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.SlgyModel
import com.otitan.ui.mview.ISlgy
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmSlgyBinding

/**
 * 森林公园
 */
class SlgyFragment : BaseFragment<FmSlgyBinding, SlgyViewModel>(), ISlgy {

    var viewmodel: SlgyViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_slgy
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): SlgyViewModel {
        if (viewmodel == null) {
            viewmodel = SlgyViewModel(activity, this)
        }
        return viewmodel as SlgyViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarSlgy.title = "保护区公园"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarSlgy)
        binding.toolbarSlgy.setNavigationOnClickListener { activity?.finish() }

        viewmodel?.let {
            BarChartInit.init(binding.chartSlgy, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableSlgy, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(SlgyDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartSlgy, list, activity,
                BarDataSet(list, "单位:公顷"))
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<SlgyModel<Any>>>()
        val name = Column<SlgyModel<Any>>("地区", "Name")
        val hztj = Column<SlgyModel<Any>>("总计面积", "Area")
        val ybhz = Column<SlgyModel<Any>>("总个数", "Count")
        columnList.add(name)
        columnList.add(hztj)
        columnList.add(ybhz)
        binding.dataTableSlgy.tableData = TableData("浙江省及各地市森林公园（单位:公顷）", tableData, columnList as List<Column<Any>>)
    }

}