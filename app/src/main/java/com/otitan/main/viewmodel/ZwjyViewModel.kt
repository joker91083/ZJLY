package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.os.Bundle
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.main.fragment.ResourceManageFragment
import com.otitan.model.*
import com.otitan.ui.mview.IInfoBase
import com.otitan.ui.mview.ILQuan
import com.otitan.ui.mview.ILycy
import com.otitan.ui.mview.IResourceManage
import com.otitan.util.GsonUtil
import com.otitan.util.ToastUtil
import com.otitan.util.Utils
import com.otitan.zjly.R
import org.jetbrains.anko.toast
import java.math.BigDecimal
import java.math.RoundingMode

class ZwjyViewModel() : BaseViewModel() {

    var data: ResultModel<List<LinkedTreeMap<String, Any>>>? = null
    val keyList = ArrayList<String>()
    val valueList = ArrayList<String>()
    val dqList = ArrayList<String>()
    var dqName = "浙江省"
    var total: LinkedTreeMap<String, Any>? = null
    val dataRepository = Injection.provideDataRepository()
    var mView: IInfoBase? = null
    val hasData = ObservableBoolean(false)
    val barChartDataList = ArrayList<BarEntry>()
    var type = 1
    var year = 2017
    //当type==3、4时 总数通过累加得到
    var count = 0

    constructor(context: Context?, mView: IInfoBase) : this() {
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
        dataRepository.zwjy(auth, type, "330000", year,
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<List<LinkedTreeMap<String, Any>>>
                        hasData.set(true)
                        if (data?.data == null || data?.data?.size == 0) {
                            mContext?.toast("没有数据")
                            hasData.set(false)
                        }
                        conversion()
                        mView?.setBarChartData(barChartDataList)
                        mView?.setTableData(conversionTableData(result))
                        mView?.setDescription()
                    }
                })
    }

    fun conversion() {
        keyList.clear()
        valueList.clear()
        barChartDataList.clear()
        if (data != null && data?.data != null) {
            var i = 0f
            count = 0
            (data?.data as List<LinkedTreeMap<String, Any>>).forEach continuing@{
                if (it["name"] == "浙江省") {
                    total = it
                    return@continuing
                }
                it.forEach { (k, v) ->
                    if (k == "name") {
                        dqList.add(v.toString())
                    } else if (k == "count") {
                        count += v.toString().split(".")[0].toInt()
                        barChartDataList.add(BarEntry(i, v?.toString()?.toFloat() ?: 0.0f))
                    }
                }
                i++
            }
        }
    }

    fun conversionTableData(data: ResultModel<List<LinkedTreeMap<String, Any>>>?): List<Any> {
        val list = ArrayList<Any>()
        if (data != null) {
            val gson = GsonUtil.getIntGson()
            val json = gson.toJson(data.data)

            val type = object : TypeToken<List<ZwjyModel.Xxzl>>() {}.type
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

}