package com.otitan.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.format.bg.BaseBackgroundFormat
import com.bin.david.form.data.style.FontStyle
import com.bin.david.form.data.style.LineStyle
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.GylcViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.GylcModel
import com.otitan.ui.mview.IGylc
import com.otitan.util.ScreenTool
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmGylcBinding

/**
 * 国有林场
 */
class GylcFragment : BaseFragment<FmGylcBinding, GylcViewModel>(), IGylc {

    var viewmodel: GylcViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_gylc
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): GylcViewModel {
        if (viewmodel == null) {
            viewmodel = GylcViewModel(activity, this)
        }
        return viewmodel as GylcViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarGylc.title = "国有林场"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarGylc)
        binding.toolbarGylc.setNavigationOnClickListener { activity?.finish() }

        viewmodel?.let {
            BarChartInit.init(binding.chartGylc, it.dqList)
        }

        SmartTableStyle.setTableStyle(binding.dataTableGylc, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(GylcDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartGylc, list, activity,
                BarDataSet(list, "单位:亩"))
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<GylcModel<Any>>>()
        val name = Column<GylcModel<Any>>("地区", "Name")
        val hztj = Column<GylcModel<Any>>("总计经营面积", "Area")
        val ybhz = Column<GylcModel<Any>>("总个数", "Count")
        columnList.add(name)
        columnList.add(hztj)
        columnList.add(ybhz)
        binding.dataTableGylc.tableData = TableData("浙江省及各地市国有林场（单位:亩）", tableData, columnList as List<Column<Any>>)
    }
}