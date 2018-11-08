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
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.model.SlfhModel
import com.otitan.model.YzlModel
import com.otitan.ui.mview.ISlfh
import com.otitan.ui.mview.IYzl
import com.otitan.util.ToastUtil
import com.otitan.zjly.R
import org.jetbrains.anko.toast

class YzlViewModel() : BaseViewModel() {

    var data: ResultModel<List<YzlModel>>? = null
    val keyList = ArrayList<String>()
    val dqList = ArrayList<String>()
    val valueList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: IYzl? = null
    val hasData = ObservableBoolean(false)
    val barChartDataList = ArrayList<BarEntry>()
    var type = 1
    var year = 2011

    constructor(context: Context?, mView: IYzl) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        getData(type, year)
    }

    fun getData(type: Int, year: Int) {
        var auth = TitanApplication.loginResult?.access_token
        if (auth == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("加载中...")
        dataRepository.yzl(auth, type, "330000", year,
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                        Log.e("tag", "数据异常：$info")
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<List<YzlModel>>
                        hasData.set(true)
                        if (data?.data == null || data?.data?.size == 0) {
                            mContext?.toast("没有数据")
                            hasData.set(false)
                        }
                        mView?.setDescription()
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
            val gson = Gson()
            val json = gson.toJson(data?.data)
            val type = object : TypeToken<List<LinkedTreeMap<String, Any>>>() {}.type
            val temp = gson.fromJson<List<LinkedTreeMap<String, Any>>>(json, type)
//            val temp = data?.data as List<LinkedTreeMap<String, Any>>
            var i = 0f
            temp.forEach continuing@{
                //                if (it["name"] == dqName) {
                if (it["name"] == "浙江省") {
                    return@continuing
                }
                it.forEach { (k, v) ->
                    if (k == "name") {
                        dqList.add(v.toString())
                    } else if (k != "code") {
                        barChartDataList.add(BarEntry(i, v?.toString()?.toFloat() ?: 0.0f))
                        keyList.add(map[k] ?: k)
                    }
                }
                i++
//                }
            }
        }
    }

    fun conversionTableData(data: ResultModel<List<YzlModel>>?): List<Any> {
        val list = ArrayList<YzlModel>()
        if (data != null) {
            val gson = Gson()
            val json = gson.toJson(data.data)
            val type = object : TypeToken<List<YzlModel>>() {}.type
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

}