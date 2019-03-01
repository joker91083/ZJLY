package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Color
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.internal.LinkedTreeMap
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.base.BindingAction
import com.otitan.base.BindingCommand
import com.otitan.data.Injection
import com.otitan.data.local.LocalDataSource
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.ResultModel
import com.otitan.ui.mview.IEventList
import com.otitan.util.FormatUtil
import com.otitan.util.Utils
import org.jetbrains.anko.toast
import java.util.*


class EventListViewModel() : BaseViewModel() {

    var data: ResultModel<List<LinkedTreeMap<String, Any>>>? = null
    private var mView: IEventList? = null
    val listType = ObservableBoolean(false)
    val startTime = ObservableField<String>()
    val endTime = ObservableField<String>()
    //已上报数据集合
    val ysbList = ArrayList<Any>()
    //未上报
    val wsbList = ArrayList<Any>()
    val dataRepository = Injection.provideDataRepository()
    val isFinishRefreshing = ObservableBoolean(false)
    val isFinishLoading = ObservableBoolean(false)
    val hasMore = ObservableBoolean(true)
    var page = 1

    constructor(context: Context?, mView: IEventList) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        startTime.set(FormatUtil.dateFormatNYR(Utils.getBeforeMonthTime(1)))
        endTime.set(FormatUtil.dateFormatNYR(Date().time))
        onRefresh.execute()
    }

    val onRefresh = BindingCommand(object : BindingAction {
        override fun call() {
            hasMore.set(true)
            page = 1
            if (listType.get()) {
                ysbList.clear()
                getList(1)
            } else {
                wsbList.clear()
                getLocList(2)
            }
        }
    })

    val onLoadMore = BindingCommand(object : BindingAction {
        override fun call() {
            if (listType.get()) {
//                getList(2)
            }
        }
    })

    fun refresh() {
        mView?.refresh()
    }

    fun search() {
        if (startTime.get().isNullOrBlank() || endTime.get().isNullOrBlank()) {
            mContext?.toast("选择搜索时间段")
            return
        }
        onRefresh.execute()
    }

    fun setTime(type: Int) {
        TimePickerBuilder(mContext, OnTimeSelectListener { date, view ->
            val s = FormatUtil.dateFormatNYR(date.time)
            if (type == 0) {
                startTime.set(s)
            } else {
                endTime.set(s)
            }
        }).setTitleText("时间选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                .setCancelColor(Color.WHITE)//取消按钮文字颜色
                .setTitleBgColor(0xFF4CAF50.toInt())//标题背景颜色 Night mode
                .setContentTextSize(18).build().show()
    }

    fun getList(requestCode: Int) {
        if (ysbList.isNotEmpty()) {
            mView?.refresh()
            return
        }
        hasMore.set(true)
        var auth = TitanApplication.sharedPreferences.getString("auth", null)
        if (auth == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("加载中...")
        dataRepository.eventList(auth, startTime.get() ?: "", endTime.get()
                ?: "", object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                when (requestCode) {
                    1 -> isFinishRefreshing.set(!isFinishRefreshing.get())
                    2 -> isFinishLoading.set(!isFinishLoading.get())
                }
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                data = result as ResultModel<List<LinkedTreeMap<String, Any>>>
                when (requestCode) {
                    1 -> {
                        ysbList.clear()
                        isFinishRefreshing.set(!isFinishRefreshing.get())
                    }
                    2 -> {
                        isFinishLoading.set(!isFinishLoading.get())
                    }
                }
                if (data?.data == null || data?.data?.size == 0) {
                    mContext?.toast("没有数据")
                    hasMore.set(false)
                    mView?.refresh()
                    return
                }
                data?.data?.let {
                    ysbList.addAll(it)
                    mView?.refresh()
                    this@EventListViewModel.page++
                }
            }
        })
    }

    fun getLocList(requestCode: Int) {
        hasMore.set(false)
        if (wsbList.isNotEmpty() && requestCode == 1) {
            mView?.refresh()
            return
        }
        dataRepository.queryEvent(object : LocalDataSource.Callback {
            override fun onFailure(info: String) {
                mContext?.toast(info)
                isFinishRefreshing.set(!isFinishRefreshing.get())
            }

            override fun onSuccess(result: Any?) {
                isFinishRefreshing.set(!isFinishRefreshing.get())
                if (result != null) {
                    wsbList.clear()
                    wsbList.addAll(result as List<Any>)
                    mView?.refresh()
                } else {
                    mContext?.toast("没有数据")
                }
            }
        })
    }
}