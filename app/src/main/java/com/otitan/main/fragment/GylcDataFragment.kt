package com.otitan.main.fragment

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.Spinner
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.otitan.base.BaseFragment
import com.otitan.main.adapter.DataManageAdapter
import com.otitan.main.adapter.ResourceManageDataAdapter
import com.otitan.main.viewmodel.GylcDataViewModel
import com.otitan.main.viewmodel.SlfhDataViewModel
import com.otitan.main.widgets.AddressPicker
import com.otitan.ui.mview.IDataBase
import com.otitan.util.TitanItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmGylcDataBinding
import com.otitan.zjly.databinding.FmSlfhDataBinding
import org.jetbrains.anko.toast

/**
 * 国有林场 数据管理
 */
class GylcDataFragment : BaseFragment<FmGylcDataBinding, GylcDataViewModel>(), IDataBase {

    var viewmodel: GylcDataViewModel? = null
    var picker: AddressPicker<Any>? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_gylc_data
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): GylcDataViewModel {
        if (viewmodel == null) {
            viewmodel = GylcDataViewModel(activity, this)
        }
        return viewmodel as GylcDataViewModel
    }

    override fun initViewObservable() {
        viewModel.isFinishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                binding.refreshLayout.finishRefreshing()
            }
        })
        viewModel.isFinishLoading.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                binding.refreshLayout.finishLoadmore()
            }
        })
        viewModel.hasMore.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                binding.refreshLayout.setEnableLoadmore((sender as ObservableBoolean).get())
            }
        })
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarGylcData.title = "数据查询"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarGylcData)
        binding.toolbarGylcData.setNavigationOnClickListener { activity?.finish() }

        if (picker == null && context != null) {
            picker = AddressPicker(context!!, "地区选择", object : OnOptionsSelectListener {
                override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                    val tx = picker?.options1Items?.get(options1)?.Name + "  " +
                            picker?.options2Items?.get(options1)?.get(options2)?.Name + "  " +
                            picker?.options3Items?.get(options1)?.get(options2)?.get(options3)?.Name
                    binding.tvAddress.text = tx

                    val code1 = picker?.options1Items?.get(options1)?.Code
                    val code2 = picker?.options2Items?.get(options1)?.get(options2)?.Code
                    val code3 = picker?.options3Items?.get(options1)?.get(options2)?.get(options3)?.Code

                    viewmodel?.let {
                        it.dqcode = if (!code3.isNullOrBlank()) {
                            code3!!
                        } else if (!code2.isNullOrBlank()) {
                            code2!!
                        } else {
                            code1!!
                        }
//                        it.onRefresh.execute()
                        startRefresh()
                    }
                }
            })
        }

        binding.rvDataManage.layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        viewmodel?.let {
            val adapter = DataManageAdapter(context, it.items, "国有林场")
            binding.rvDataManage.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val item = menu?.findItem(R.id.data_search)
        val searchView = item?.actionView as SearchView?
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewmodel?.keyWord = query
//                    viewmodel?.onRefresh?.execute()
                    startRefresh()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewmodel?.keyWord = newText ?: ""
                return false
            }
        })
    }

    override fun startRefresh() {
        binding.refreshLayout.startRefresh()
    }

    override fun refresh() {
        val adapter = binding.rvDataManage.adapter
        if (adapter != null && viewmodel != null) {
            (adapter as DataManageAdapter).setData(viewmodel!!.items, null)
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