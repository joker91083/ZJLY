package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.util.Log
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.GylcModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.IGylc
import com.otitan.util.ToastUtil
import org.jetbrains.anko.toast

class GylcViewModel() : BaseViewModel() {

    var data: ResultModel<List<LinkedTreeMap<String, Any>>>? = null
    val keyList = ArrayList<String>()
    val dqList = ArrayList<String>()
    val valueList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: IGylc? = null
    val hasData = ObservableBoolean(false)
    val barChartDataList = ArrayList<BarEntry>()
    var gylc = GylcModel<Any>()

    constructor(context: Context?, mView: IGylc) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        getData()
    }

    fun getData() {
        val info = TitanApplication.loginResult
        if (info?.access_token == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        val auth = "Bearer ${info.access_token}"
        showDialog("加载中...")
        dataRepository.gylc(auth, info.code ?: "",
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                        Log.e("tag", "数据异常：$info")
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<List<LinkedTreeMap<String, Any>>>
                        hasData.set(true)
                        if (data?.data == null || data?.data?.size == 0) {
                            mContext?.toast("没有数据")
                            hasData.set(false)
                        }
                        conversion(dqName)
                        mView?.setBarChartData(barChartDataList)
                        mView?.setTableData(conversionTableData(result))
                        mView?.setDescription()
                    }
                })
    }

    fun conversion(dqName: String) {
        valueList.clear()
        barChartDataList.clear()
        if (data != null && data?.data != null) {
//            array?.forEach {
//                val values = it.split(",")
//                map[values[0]] = values[1]
//            }
            val temp = data?.data as List<LinkedTreeMap<String, Any>>
            var i = 0f
            temp.forEach continuing@{
                if (it["Name"] == "浙江省") {
                    return@continuing
                }
                it.forEach { (k, v) ->
                    when (k) {
                        "Count" -> gylc.Count += (v?.toString()?.split(".")?.get(0)?.toInt() ?: 0)
                        "Name" -> dqList.add(v.toString())
                        "Area" -> {
                            gylc.Area += (v?.toString()?.toDouble() ?: 0.0)
                            barChartDataList.add(BarEntry(i, v?.toString()?.toFloat() ?: 0.0f))
                        }
                    }
                }
                i++
            }
        }
    }

    fun conversionTableData(data: ResultModel<List<LinkedTreeMap<String, Any>>>?): List<Any> {
        val list = ArrayList<GylcModel<Any>>()
        if (data != null) {
            val gson = Gson()
            val json = gson.toJson(data.data)
            val type = object : TypeToken<List<GylcModel<Any>>>() {}.type
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

}