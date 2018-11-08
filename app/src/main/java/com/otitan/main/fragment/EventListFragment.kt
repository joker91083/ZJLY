package com.otitan.main.fragment

import android.databinding.Observable
import android.databinding.ObservableBoolean
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
import com.otitan.main.viewmodel.EventListViewModel
import com.otitan.main.viewmodel.LdzzDataViewModel
import com.otitan.main.viewmodel.LycyDataViewModel
import com.otitan.main.viewmodel.SlfhDataViewModel
import com.otitan.main.widgets.AddressPicker
import com.otitan.ui.mview.IDataBase
import com.otitan.ui.mview.IEventList
import com.otitan.util.TitanItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmEventListBinding
import com.otitan.zjly.databinding.FmLdzzDataBinding
import com.otitan.zjly.databinding.FmLycyDataBinding
import com.otitan.zjly.databinding.FmSlfhDataBinding
import org.jetbrains.anko.toast

/**
 * 事件列表
 */
class EventListFragment : BaseFragment<FmEventListBinding, EventListViewModel>(), IEventList {

    var viewmodel: EventListViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_event_list
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): EventListViewModel {
        if (viewmodel == null) {
            viewmodel = EventListViewModel(activity, this)
        }
        return viewmodel as EventListViewModel
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
//                binding.refreshLayout.setEnableLoadmore((sender as ObservableBoolean).get())
            }
        })
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbarEventList.title = "事件列表"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarEventList)
        binding.toolbarEventList.setNavigationOnClickListener { activity?.finish() }
        binding.refreshLayout.setEnableLoadmore(false)
        binding.rvEventList.layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        viewmodel?.let {
            val adapter = DataManageAdapter(context, it.wsbList, "事件列表")
            binding.rvEventList.adapter = adapter
        }
    }

    override fun refresh() {
        val adapter = binding.rvEventList.adapter
        if (adapter != null && viewmodel != null) {
            (adapter as DataManageAdapter).setEventLisstener(this)
            if (viewmodel!!.listType.get()) {
                adapter.setData(viewmodel!!.ysbList, 2)
            } else {
                adapter.setData(viewmodel!!.wsbList, 1)
            }
        }
    }

    override fun getNewData() {
        viewmodel?.onRefresh?.execute()
    }

}