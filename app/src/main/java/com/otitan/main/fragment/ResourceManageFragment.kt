package com.otitan.main.fragment

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.*
import android.widget.AdapterView
import android.widget.Spinner
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.table.TableData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.internal.LinkedTreeMap
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.ResourceManageViewModel
import com.otitan.main.widgets.BarChartInit
import com.otitan.main.widgets.SmartTableStyle
import com.otitan.model.ResourceModel
import com.otitan.ui.mview.IResourceManage
import com.otitan.util.Utils
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmResourceManageBinding
import java.util.regex.Pattern

/**
 * 资源管护
 */
class ResourceManageFragment : BaseFragment<FmResourceManageBinding, ResourceManageViewModel>(), IResourceManage {

    var viewmodel: ResourceManageViewModel? = null
    var typeName = "林地面积"

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_resource_manage
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): ResourceManageViewModel {
        if (viewmodel == null) {
            viewmodel = ResourceManageViewModel(activity, this)
        }
        return viewmodel as ResourceManageViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarResource.title = "资源管理"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarResource)
        binding.toolbarResource.setNavigationOnClickListener { activity?.finish() }

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
            BarChartInit.init(binding.chartResource, it.dqList)
        }
        SmartTableStyle.setTableStyle(binding.dataTableResource, context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_data_manage, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.data_manage -> {
                startContainerActivity(ResourceManageDataFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun setDescription() {
        if (viewmodel?.hasData?.get() != true) {
            return
        }
        val obj = viewmodel?.data?.data?.get(0) ?: return
        val value = when (viewmodel?.type) {
            1 -> obj["Area"].toString()
            2 -> obj["Area"].toString()
            3 -> obj["Area"].toString()
            4 -> obj["Area"].toString()
            else -> obj["Area"].toString()
        }
        var gjgyl = ""
        var dfgyl = ""
        if (viewmodel?.type == 3) {
            gjgyl = obj["CountryArea"].toString()
            dfgyl = obj["LocalArea"].toString()
        }
        val s = when (viewmodel?.type) {
            1, 2 -> "${viewmodel?.year}年浙江省$typeName${value}万亩"
            3 -> "${viewmodel?.year}年浙江省$typeName${value}万亩，其中：国家公益林：${gjgyl}万亩，地方公益林：${dfgyl}万亩"
            4 -> "${viewmodel?.year}年浙江省$typeName$value%"
            else -> "${viewmodel?.year}年浙江省$typeName${value}万m³"
        }
        binding.pestTvDes.text = Utils.getSpanned(s)
    }


    override fun setBarChartData(list: ArrayList<BarEntry>) {
        BarChartInit.setBarChartData(binding.chartResource, list, activity,
                when (viewmodel?.type) {
                    1, 2, 3 -> BarDataSet(list, "单位:万亩")
                    4 -> BarDataSet(list, "单位:%")
                    else -> BarDataSet(list, "单位:万m³")
                })
    }

    override fun setTableData(tableData: List<Any>) {
        when (viewmodel?.type) {
            1 -> {
                val columnList = ArrayList<Column<ResourceModel.LDMJModel>>()
                val name = Column<ResourceModel.LDMJModel>("地区", "Name")
                val area = Column<ResourceModel.LDMJModel>("林地总面积", "Area")
                val QMLD = Column<ResourceModel.LDMJModel>("乔木林地", "QMLD")
                val ZLD = Column<ResourceModel.LDMJModel>("竹林地", "ZLD")
                val SLD = Column<ResourceModel.LDMJModel>("疏林地", "SLD")
                val TSGMLD = Column<ResourceModel.LDMJModel>("特殊灌木林地", "TSGMLD")
                val YBGMLD = Column<ResourceModel.LDMJModel>("一般灌木林地", "YBGMLD")
                val WCLZLD = Column<ResourceModel.LDMJModel>("未成林造林地", "WCLZLD")
                val YLD = Column<ResourceModel.LDMJModel>("宜林地", "YLD")
                val other = Column<ResourceModel.LDMJModel>("其它", "Other")
                columnList.add(name)
                columnList.add(area)
                columnList.add(QMLD)
                columnList.add(ZLD)
                columnList.add(SLD)
                columnList.add(TSGMLD)
                columnList.add(YBGMLD)
                columnList.add(WCLZLD)
                columnList.add(YLD)
                columnList.add(other)

                binding.dataTableResource.tableData = TableData("浙江省及各地市林地总面积（单位:万亩）", tableData, columnList as List<Column<Any>>)
            }
            2 -> {
                val columnList = ArrayList<Column<ResourceModel.SLMJModel>>()
                val name = Column<ResourceModel.SLMJModel>("地区", "Name")
                val area = Column<ResourceModel.SLMJModel>("森林面积", "Area")
                columnList.add(name)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市森林总面积（单位:万亩）", tableData, columnList as List<Column<Any>>)
            }
            3 -> {
                val columnList = ArrayList<Column<ResourceModel.GYLMJModel>>()
                val name = Column<ResourceModel.GYLMJModel>("地区", "Name")
                val cArea = Column<ResourceModel.GYLMJModel>("国家级公益林", "CountryArea")
                val lArea = Column<ResourceModel.GYLMJModel>("地方公益林", "LocalArea")
                val area = Column<ResourceModel.GYLMJModel>("合计", "Area")
                columnList.add(name)
                columnList.add(cArea)
                columnList.add(lArea)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市公益林总面积（单位:万亩）", tableData, columnList as List<Column<Any>>)
            }
            4 -> {
                val columnList = ArrayList<Column<ResourceModel.SLFGLModel>>()
                val name = Column<ResourceModel.SLFGLModel>("地区", "Name")
                val area = Column<ResourceModel.SLFGLModel>("森林覆盖率", "Area")
                columnList.add(name)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市森林覆盖率（单位:%）", tableData, columnList as List<Column<Any>>)
            }
            5 -> {
                val columnList = ArrayList<Column<ResourceModel.HLMXJModel>>()
                val name = Column<ResourceModel.HLMXJModel>("地区", "Name")
                val area = Column<ResourceModel.HLMXJModel>("活立木蓄积", "Area")
                columnList.add(name)
                columnList.add(area)
                binding.dataTableResource.tableData = TableData("浙江省及各地市活立木蓄积（单位:万m³）", tableData, columnList as List<Column<Any>>)
            }
        }
    }
}