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
import com.otitan.main.viewmodel.LdzzViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.LdzzModel
import com.otitan.ui.mview.ILdzz
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLdzzBinding

/**
 * 林地征占
 */
class LdzzFragment : BaseFragment<FmLdzzBinding, LdzzViewModel>(), ILdzz {

    var viewmodel: LdzzViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_ldzz
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): LdzzViewModel {
        if (viewmodel == null) {
            viewmodel = LdzzViewModel(activity, this)
        }
        return viewmodel as LdzzViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarLdzz.title = "林地征占"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarLdzz)
        binding.toolbarLdzz.setNavigationOnClickListener { activity?.finish() }

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
                    it.getData(p2 + 1, it.year)
                    it.type = p2 + 1
                }
            }
        }

        viewmodel?.let {
            BarChartInit.init(binding.chartLdzz, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableLdzz, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                val menu = TitanApplication.loginResult?.menu
                if (Utils.checkPermission(activity, menu?.APP_LQGL_LDZZ_SJCX)) {
                    startContainerActivity(LdzzDataFragment::class.java.canonicalName)
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
        val s = when (viewmodel?.type) {
            1 -> "${viewmodel?.year}年浙江省项目个数为：${obj["Count"]}个"
            2 -> "${viewmodel?.year}年浙江省征占用地总面积${obj["Total"]}公顷"
            else -> "${viewmodel?.year}年浙江省林地定额为：${obj["Count"]}公顷"
        }
        binding.pestTvDes.text = Utils.getSpanned(s)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartLdzz, list, activity,
                when (viewmodel?.type) {
                    1 -> BarDataSet(list, "单位:个")
                    2 -> BarDataSet(list, "单位:公顷")
                    else -> BarDataSet(list, "单位:公顷")
                })
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<LdzzModel.Xmgs>>()
                val name = Column<LdzzModel.Xmgs>("地区", "Name")
                val count = Column<LdzzModel.Xmgs>("项目个数", "Count")
                columnList.add(name)
                columnList.add(count)
                binding.dataTableLdzz.tableData = TableData("浙江省及各区县项目个数", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<LdzzModel.Zzyldmj>>()
                val name = Column<LdzzModel.Zzyldmj>("地区", "Name")
                val column1 = Column<LdzzModel.Zzyldmj>("征占用地总面积", "Total")
                val column2 = Column<LdzzModel.Zzyldmj>("防护林", "FHL")
                val column3 = Column<LdzzModel.Zzyldmj>("特用林", "TYL")
                val column4 = Column<LdzzModel.Zzyldmj>("用材林", "YCL")
                val column5 = Column<LdzzModel.Zzyldmj>("经济林", "JJL")
                val column6 = Column<LdzzModel.Zzyldmj>("薪炭林", "XTL")
                val column7 = Column<LdzzModel.Zzyldmj>("用材(经济,薪炭)林采伐迹地", "YCLCFJD")
                val column8 = Column<LdzzModel.Zzyldmj>("苗圃地", "MPD")
                val column9 = Column<LdzzModel.Zzyldmj>("未成林造地", "WCLZD")
                val column10 = Column<LdzzModel.Zzyldmj>("疏林地,灌木林地", "SLD")
                val column11 = Column<LdzzModel.Zzyldmj>("其他", "QT")
                columnList.add(name)
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
                binding.dataTableLdzz.tableData = TableData("浙江省及各区县征占用地面积 单位:公顷", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<LdzzModel.Ldde>>()
                val name = Column<LdzzModel.Ldde>("地区", "Name")
                val count = Column<LdzzModel.Ldde>("林地定额", "Count")
                columnList.add(name)
                columnList.add(count)
                binding.dataTableLdzz.tableData = TableData("浙江省及各区县林地定额", tableData, columnList as List<Column<Any>>)
            }
        }
    }

}