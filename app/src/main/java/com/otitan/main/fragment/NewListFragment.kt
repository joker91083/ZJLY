package com.otitan.main.fragment

import android.databinding.Observable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.main.adapter.NewListAdapter
import com.otitan.main.viewmodel.NewListViewModel
import com.otitan.ui.mview.INewList
import com.otitan.util.TitanItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmNewListBinding

class NewListFragment() : BaseFragment<FmNewListBinding, NewListViewModel>(), INewList {
    var viewmodel: NewListViewModel? = null
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_new_list
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): NewListViewModel {
        if (viewmodel == null) {
            viewmodel = NewListViewModel(activity, this)
        }
        return viewmodel as NewListViewModel
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
    }

    override fun initData() {
        super.initData()
        viewmodel?.type = arguments?.getInt("type", 1) ?: 1
        binding.refreshLayout.setEnableLoadmore(false)
        val layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        binding.rvNewList.layoutManager = layoutManager
        binding.rvNewList.addItemDecoration(TitanItemDecoration(activity!!, 1, 10))
        val adapter = NewListAdapter(activity, viewmodel?.data)
        binding.rvNewList.adapter = adapter
    }

    override fun refresh() {
        val adapter = binding.rvNewList.adapter
        if (adapter is NewListAdapter) {
            adapter.items = viewmodel?.data
            adapter.notifyDataSetChanged()
        }
    }
}