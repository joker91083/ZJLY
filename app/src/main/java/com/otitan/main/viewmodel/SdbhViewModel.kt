package com.otitan.main.viewmodel

import android.content.Context
import android.util.Log
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.*
import com.otitan.ui.mview.*
import com.otitan.util.ToastUtil
import com.otitan.zjly.R
import org.jetbrains.anko.toast

class SdbhViewModel() : BaseViewModel() {

    var data: ResultModel<Any>? = null
    val keyList = ArrayList<String>()
    val dqList = ArrayList<String>()
    val valueList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: IGylc? = null
    val barChartDataList = ArrayList<BarEntry>()

    constructor(context: Context?, mView: IGylc) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        getData()
    }

    fun getData() {
        var auth = TitanApplication.loginResult?.access_token
        if (auth == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("加载中...")
        dataRepository.sdbh(auth,  "330000",
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                        Log.e("tag", "数据异常：$info")
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<Any>
                        if (data?.data == null || (data?.data!! as List<Any>).size == 0) {
                            mContext?.toast("没有数据")
                        }
                        conversion(dqName)
                        mView?.setBarChartData(barChartDataList)
                        mView?.setTableData(conversionTableData(result))
                    }
                })
    }

    fun conversion(dqName: String) {
        keyList.clear()
        valueList.clear()
        barChartDataList.clear()
        if (data != null && data?.data != null) {
            val array = mContext?.resources?.getStringArray(R.array.yzl)
            val map = HashMap<String, String>()
            array?.forEach {
                val values = it.split(",")
                map[values[0]] = values[1]
            }
            val temp = data?.data as List<LinkedTreeMap<String, Any>>
            var i = 0f
            temp.forEach {
                it.forEach { (k, v) ->
                    if (k == "Name") {
                        dqList.add(v.toString())
                    } else if (k == "AllArea") {
                        barChartDataList.add(BarEntry(i, v?.toString()?.toFloat() ?: 0.0f))
                        keyList.add(map[k] ?: k)
                    }
                }
                i++
            }
        }
    }

    fun conversionTableData(data: ResultModel<Any>?): List<Any> {
        val list = ArrayList<SdbhModel<Any>>()
        if (data != null) {
            val gson = Gson()
            val json = gson.toJson(data.data)
            val type = object : TypeToken<List<SdbhModel<Any>>>() {}.type
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

}