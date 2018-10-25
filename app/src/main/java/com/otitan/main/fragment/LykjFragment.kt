package com.otitan.main.fragment

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.otitan.base.BaseFragment
import com.otitan.main.adapter.DataManageAdapter
import com.otitan.main.viewmodel.LykjViewModel
import com.otitan.ui.mview.ILykj
import com.otitan.util.TitanItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLykjBinding

/**
 * 林业科技
 */
class LykjFragment : BaseFragment<FmLykjBinding, LykjViewModel>(), ILykj {

    var viewmodel: LykjViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_lykj
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): LykjViewModel {
        if (viewmodel == null) {
            viewmodel = LykjViewModel(activity, this)
        }
        return viewmodel as LykjViewModel
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
        binding.toolbarLykj.title = "林业科技"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarLykj)
        binding.toolbarLykj.setNavigationOnClickListener { activity?.finish() }

        binding.spinnerType.setSelection(0, true)
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    it.hasMore.set(true)
                    it.getData(p2 + 3, 1, 1)
                    it.type.set(p2 + 3)
                }
            }
        }

        binding.spinnerIndex.setSelection(0, true)
        binding.spinnerIndex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewmodel?.let {
                    it.hasMore.set(true)
                    val index = (p0 as Spinner).getItemAtPosition(p2).toString()
                    if (index == "科技标准") {
                        it.getData(binding.spinnerType.selectedItemPosition + 3, 1, 1)
                        it.type.set(binding.spinnerType.selectedItemPosition + 3)
                    } else if (index != "") {
                        it.getData(p2 + 1, 1, 1)
                        it.type.set(p2 + 1)
                    }
                }
            }
        }

        val adapter = DataManageAdapter(activity, viewmodel?.data?.data?.rows, "林业科技")
        val l = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        binding.rvLykj.layoutManager = l
        binding.rvLykj.adapter = adapter
    }

    override fun refresh(items: List<Any>?) {
        val adapter = binding.rvLykj.adapter
        if (adapter != null) {
            (adapter as DataManageAdapter).setData(items, viewmodel?.type?.get() ?: 1)
        }
    }
}