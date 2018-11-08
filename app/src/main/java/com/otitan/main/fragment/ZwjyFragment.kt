package com.otitan.main.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.LQuanViewModel
import com.otitan.main.viewmodel.ZwjyViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.LQuanModel
import com.otitan.ui.mview.IInfoBase
import com.otitan.ui.mview.ILQuan
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLquanBinding
import com.otitan.zjly.databinding.FmZwjyBinding

/**
 * 植物检疫
 */
class ZwjyFragment : BaseFragment<FmZwjyBinding, ZwjyViewModel>(), IInfoBase {

    var viewmodel: ZwjyViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_zwjy
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): ZwjyViewModel {
        if (viewmodel == null) {
            viewmodel = ZwjyViewModel(activity, this)
        }
        return viewmodel as ZwjyViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbar.title = "植物检疫"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }

        binding.spinnerYear.setSelection(0, true)
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    val year = (p0 as Spinner).getItemAtPosition(p2).toString().toInt()
                    it.year = year
                    it.getData(it.type, year)
                }
            }
        }

        binding.spinnerIndex.setSelection(0, true)
        binding.spinnerIndex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    it.type = p2 + 1
                }
                when (p2) {
                    0 -> {
                        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.nianfen_zwjy1))
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
                        binding.spinnerYear.adapter = adapter
                    }
                    1, 2, 3 -> {
                        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.nianfen_zwjy2))
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
                        binding.spinnerYear.adapter = adapter
                    }
                }
            }
        }

        viewmodel?.let {
            BarChartInit.init(binding.chart, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTable, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(ZwjyDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setDescription() {
        if (viewmodel?.hasData?.get() != true) {
            return
        }
        val count = viewmodel?.total?.get("count").toString().split(".")[0]
        val s = when (viewmodel?.type) {
            1 -> "${viewmodel?.year}年浙江省国内调运检疫报检单：${count}本"
            2 -> "${viewmodel?.year}年浙江省植物检疫证书：${count}本"
            3 -> "${viewmodel?.year}年浙江省产地检疫报检：${viewmodel?.count}本"
            4 -> "${viewmodel?.year}年浙江省产地检疫合格证：${viewmodel?.count}本"
            else -> ""
        }
        binding.pestTvDes.text = Utils.getSpanned(s)
    }


    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chart, list, activity,
                BarDataSet(list, "单位:本"))
    }

    override fun setTableData(tableData: List<Any>) {
        val columnList = ArrayList<Column<LQuanModel.Xxzl>>()
        val name = Column<LQuanModel.Xxzl>("地区", "name")
        val count = Column<LQuanModel.Xxzl>("数量", "count")
        columnList.add(name)
        columnList.add(count)
        var tableName = ""
        when (viewmodel?.type) {
            1 -> tableName = "${viewmodel?.year}年浙江省及各地市调运检疫报检单(单位:本)"
            2 -> tableName = "${viewmodel?.year}年浙江省及各地市植物检疫证书(单位:本)"
            3 -> tableName = "${viewmodel?.year}年浙江各地市产地检疫报检(单位:本)"
            4 -> tableName = "${viewmodel?.year}年浙江各地市产地检疫合格证(单位:本)"
        }
        binding.dataTable.tableData = TableData(tableName, tableData, columnList as List<Column<Any>>)
    }

}