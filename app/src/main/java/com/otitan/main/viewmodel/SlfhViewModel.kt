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
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.model.SlfhModel
import com.otitan.ui.mview.ISlfh
import com.otitan.util.ToastUtil
import com.otitan.zjly.R
import org.jetbrains.anko.toast

class SlfhViewModel() : BaseViewModel() {

    var data: ResultModel<Any>? = null
    val keyList = ArrayList<String>()
    val valueList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: ISlfh? = null
    val barChartDataList = ArrayList<BarEntry>()
    var type = 1
    var year = 2013
    val arrayList = arrayOf(R.array.slfh1, R.array.slfh2, R.array.slfh3, R.array.slfh4, R.array.slfh5, R.array.slfh6)

    constructor(context: Context?, mView: ISlfh) : this() {
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
        dataRepository.slfh(auth, type, "330000", year,
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
            val array = mContext?.resources?.getStringArray(arrayList[type - 1])
            val map = HashMap<String, String>()
            array?.forEach {
                val values = it.split(",")
                map[values[0]] = values[1]
            }
            (data?.data as List<LinkedTreeMap<String, Any>>).forEach {
                if (it["Name"] == dqName) {
                    var i = 0f
                    it.forEach { (k, v) ->
                        if (k != "Name" && k != "Code") {
                            val v1 = v.toString().replace(",", "")
                            barChartDataList.add(BarEntry(i, v1.toFloat()))
                            keyList.add(map[k] ?: k)
                            valueList.add(v1)
                            i++
                        }
                    }
                }
            }
        }
    }

    fun conversionTableData(data: ResultModel<Any>?): List<Any> {
        val list = ArrayList<Any>()
        if (data != null) {
            val gson = Gson()
            val json = gson.toJson(data.data)

            val type = when (type) {
                1 -> object : TypeToken<List<SlfhModel.Hzcs>>() {}.type
                2 -> object : TypeToken<List<SlfhModel.Hcmj>>() {}.type
                3 -> object : TypeToken<List<SlfhModel.Ssmj>>() {}.type
                4 -> object : TypeToken<List<SlfhModel.Phjf>>() {}.type
                5 -> object : TypeToken<List<SlfhModel.Rysw>>() {}.type
                else -> object : TypeToken<List<SlfhModel.Sslm>>() {}.type
            }
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

}