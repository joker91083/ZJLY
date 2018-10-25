package com.otitan.main.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.otitan.base.BaseFragment
import com.otitan.main.adapter.DataManageAdapter
import com.otitan.main.adapter.ResourceManageDataAdapter
import com.otitan.main.viewmodel.ResourceManageDataViewModel
import com.otitan.main.viewmodel.YzlDataViewModel
import com.otitan.main.widgets.AddressPicker
import com.otitan.ui.mview.IDataBase
import com.otitan.util.TitanItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmResourceManageDataBinding
import com.otitan.zjly.databinding.FmYzlDataBinding
import org.jetbrains.anko.toast

/**
 * 营造林 数据管理
 */
class YzlDataFragment : BaseFragment<FmYzlDataBinding, YzlDataViewModel>(), IDataBase {

    var viewmodel: YzlDataViewModel? = null
    var picker: AddressPicker<Any>? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_yzl_data
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): YzlDataViewModel {
        if (viewmodel == null) {
            viewmodel = YzlDataViewModel(activity, this)
        }
        return viewmodel as YzlDataViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarYzlData.title = "数据管理"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarYzlData)
        binding.toolbarYzlData.setNavigationOnClickListener { activity?.finish() }

        if (picker == null && context != null) {
            picker = AddressPicker(context!!, "地区选择", object : OnOptionsSelectListener {
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                    val tx = picker?.options1Items?.get(options1)?.Name + "  " +
                            picker?.options2Items?.get(options1)?.get(options2)?.Name + "  " +
                            picker?.options3Items?.get(options1)?.get(options2)?.get(options3)?.Name
                    val code1 = picker?.options1Items?.get(options1)?.Code
                    val code2 = picker?.options2Items?.get(options1)?.get(options2)?.Code
                    val code3 = picker?.options3Items?.get(options1)?.get(options2)?.get(options3)?.Code
                    viewmodel?.dqcode = if (!code3.isNullOrBlank()) {
                        code3!!
                    } else if (!code2.isNullOrBlank()) {
                        code2!!
                    } else {
                        code1!!
                    }
                    binding.tvAddress.text = tx
                    viewmodel?.request()
//                    viewmodel?.let {
//                        it.getData(it.type, it.year, it.dqcode)
//                    }
                }
            })
        }

        binding.spinnerYear.setSelection(0, true)
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                viewmodel?.let {
                val year = (p0 as Spinner).getItemAtPosition(p2).toString().toInt()
                viewmodel?.year = year
                viewmodel?.request()
//                    it.getData(it.type, year, it.dqcode)
//                }
            }
        }

        binding.spinnerIndex.setSelection(0, true)
        binding.spinnerIndex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                viewmodel?.let {
                viewmodel?.type = p2 + 1
                viewmodel?.request()
//                    it.getData(p2 + 1, it.year, it.dqcode)
//                }
            }
        }

        binding.rvDataManage.layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
//        binding.rvDataManage.addItemDecoration(TitanItemDecoration(activity!!, 1, 10))
        viewmodel?.let {
            val adapter = DataManageAdapter(activity, it.items, "营造林")
            binding.rvDataManage.adapter = adapter
        }
    }

    override fun refresh() {
        val adapter = binding.rvDataManage.adapter
        if (adapter != null && viewmodel != null) {
            (adapter as DataManageAdapter).setData(viewmodel!!.items, viewmodel!!.type)
        }
    }

    override fun showPicker() {
        if (picker?.isLoaded == true) {
            picker?.picker?.show()
        } else {
            context?.let {
                context?.toast("数据初始化中，请稍候")
            }
        }
    }

}