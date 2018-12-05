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
import com.otitan.main.viewmodel.SlfhViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.SlfhModel
import com.otitan.ui.mview.ISlfh
import com.otitan.util.GsonUtil
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmSlfhBinding
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * 森林防火
 */
class SlfhFragment : BaseFragment<FmSlfhBinding, SlfhViewModel>(), ISlfh {

    var viewmodel: SlfhViewModel? = null
    var typeName = "火灾次数"

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_slfh
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): SlfhViewModel {
        if (viewmodel == null) {
            viewmodel = SlfhViewModel(activity, this)
        }
        return viewmodel as SlfhViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarSlfh.title = "森林防火"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarSlfh)
        binding.toolbarSlfh.setNavigationOnClickListener { activity?.finish() }

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
                    typeName = (p0 as Spinner).getItemAtPosition(p2).toString()
                    it.getData(p2 + 1, it.year)
                    it.type = p2 + 1
                }
            }
        }

        viewmodel?.let {
            BarChartInit.init(binding.chartSlfh, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableSlfh, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                val menu = TitanApplication.loginResult?.menu
                if (Utils.checkPermission(activity, menu?.APP_LQGL_SLFH_SJCX)) {
                    startContainerActivity(SlfhDataFragment::class.java.canonicalName)
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
        var hzcs: SlfhModel.Hzcs? = null
        var rysw: SlfhModel.Rysw? = null
        var sslm: SlfhModel.Sslm? = null
        val gson = GsonUtil.getIntGson()
        when (viewmodel?.type) {
            1 -> {
                val json = gson.toJson(obj)
                hzcs = gson.fromJson(json, SlfhModel.Hzcs::class.java)
            }
            5 -> {
                val json = gson.toJson(obj)
                rysw = gson.fromJson(json, SlfhModel.Rysw::class.java)
            }
            6 -> {
                val json = gson.toJson(obj)
                sslm = gson.fromJson(json, SlfhModel.Sslm::class.java)
            }
        }
        var value = ""
        when (viewmodel?.type) {
            2, 3, 4 -> value = obj["TotalFireCount"].toString()
        }
        val s = when (viewmodel?.type) {
            1 -> "${viewmodel?.year}年浙江省$typeName${hzcs?.TotalFireCount}次，其中：一般火灾次数：${hzcs?.NormalFire}次，" +
                    "较大火灾次数：${hzcs?.LargeFire}次，重大火灾次数：${hzcs?.MajorFire}次，特大火灾次数：${hzcs?.OversizeFire}次"
            2 -> "${viewmodel?.year}年浙江省$typeName${value}亩"
            3 -> "${viewmodel?.year}年浙江省$typeName${value}亩"
            4 -> "${viewmodel?.year}年浙江省$typeName${value}万元"
            5 -> "${viewmodel?.year}年浙江省火灾${typeName}情况${rysw?.TotalCount}次，" +
                    "其中：轻伤：${rysw?.Minor}人，重伤：${rysw?.Serious}人，死亡：${rysw?.Death}人"
            else -> "${viewmodel?.year}年浙江省${typeName}情况为，成林蓄积损失：${sslm?.Accumulation}立方米，幼苗损失：${sslm?.Seedling}万株"
        }
        binding.pestTvDes.text = Utils.getSpanned(s)
    }

    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartSlfh, list, activity,
                when (viewmodel?.type) {
                    1 -> BarDataSet(list, "单位:次")
                    2, 3 -> BarDataSet(list, "单位:亩")
                    4 -> BarDataSet(list, "单位:万元")
                    5 -> BarDataSet(list, "单位:人")
                    else -> BarDataSet(list, "单位:m³/万株")
                })
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<SlfhModel.Hzcs>>()
                val name = Column<SlfhModel.Hzcs>("地区", "Name")
                val hztj = Column<SlfhModel.Hzcs>("火灾合计", "TotalFireCount")
                val ybhz = Column<SlfhModel.Hzcs>("一般火灾", "NormalFire")
                val jdhz = Column<SlfhModel.Hzcs>("较大火灾", "LargeFire")
                val zdhz = Column<SlfhModel.Hzcs>("重大火灾", "MajorFire")
                val tdhz = Column<SlfhModel.Hzcs>("特大火灾", "OversizeFire")
                columnList.add(name)
                columnList.add(hztj)
                columnList.add(ybhz)
                columnList.add(jdhz)
                columnList.add(zdhz)
                columnList.add(tdhz)

                binding.dataTableSlfh.tableData = TableData("浙江省及各地市火灾次数（单位:次）", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<SlfhModel.Hcmj>>()
                val name = Column<SlfhModel.Hcmj>("地区", "Name")
                val hcmj = Column<SlfhModel.Hcmj>("火场面积", "TotalFireCount")
                columnList.add(name)
                columnList.add(hcmj)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市火场面积（单位:亩）", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<SlfhModel.Ssmj>>()
                val name = Column<SlfhModel.Ssmj>("地区", "Name")
                val ssmj = Column<SlfhModel.Ssmj>("损失面积", "TotalFireCount")
                columnList.add(name)
                columnList.add(ssmj)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市损失面积（单位:亩）", tableData, columnList as List<Column<Any>>)
            }
            4 -> {
                val columnList = ArrayList<Column<SlfhModel.Phjf>>()
                val name = Column<SlfhModel.Phjf>("地区", "Name")
                val phjf = Column<SlfhModel.Phjf>("扑火经费", "TotalFireCount")
                columnList.add(name)
                columnList.add(phjf)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市扑火经费（单位:万元）", tableData, columnList as List<Column<Any>>)
            }
            5 -> {
                val columnList = ArrayList<Column<SlfhModel.Rysw>>()
                val name = Column<SlfhModel.Rysw>("地区", "Name")
                val totalCount = Column<SlfhModel.Rysw>("合计", "TotalCount")
                val minor = Column<SlfhModel.Rysw>("轻伤", "Minor")
                val serious = Column<SlfhModel.Rysw>("重伤", "Serious")
                val death = Column<SlfhModel.Rysw>("死亡", "Death")
                columnList.add(name)
                columnList.add(totalCount)
                columnList.add(minor)
                columnList.add(serious)
                columnList.add(death)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市火灾人员伤亡（单位:人）", tableData, columnList as List<Column<Any>>)
            }
            else -> {
                val columnList = ArrayList<Column<SlfhModel.Sslm>>()
                val name = Column<SlfhModel.Sslm>("地区", "Name")
                val accumulation = Column<SlfhModel.Sslm>("成林蓄积(m³)", "Accumulation")
                val seedling = Column<SlfhModel.Sslm>("幼苗株数(万株)", "Seedling")
                columnList.add(name)
                columnList.add(accumulation)
                columnList.add(seedling)
                binding.dataTableSlfh.tableData = TableData("浙江省及各地市林木损失", tableData, columnList as List<Column<Any>>)
            }
        }
    }

}