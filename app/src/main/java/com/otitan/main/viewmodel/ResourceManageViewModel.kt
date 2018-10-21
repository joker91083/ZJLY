package com.otitan.main.viewmodel

import android.content.Context
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
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.IResourceManage
import com.otitan.util.ToastUtil
import com.otitan.util.Utils
import com.otitan.zjly.R
import org.jetbrains.anko.toast

class ResourceManageViewModel() : BaseViewModel() {

    var data: ResultModel<Any>? = null
    val keyList = ArrayList<String>()
    val valueList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: IResourceManage? = null
    val barChartDataList = ArrayList<BarEntry>()
    var type = 1
    var year = 2009
    val arrayList = arrayOf(R.array.zyghqk1, R.array.zyghqk2, R.array.zyghqk3, R.array.zyghqk4, R.array.zyghqk5)

    constructor(context: Context?, mView: IResourceManage) : this() {
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
        dataRepository.resourceManage(auth, type, "330000", year,
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<Any>
                        if (data?.data == null||(data?.data!! as List<Any>).size==0) {
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
                    it.forEach { k, v ->
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
                1 -> object : TypeToken<List<ResourceModel.LDMJModel>>() {}.type
                2 -> object : TypeToken<List<ResourceModel.SLMJModel>>() {}.type
                3 -> object : TypeToken<List<ResourceModel.GYLMJModel>>() {}.type
                4 -> object : TypeToken<List<ResourceModel.SLFGLModel>>() {}.type
                else -> object : TypeToken<List<ResourceModel.HLMXJModel>>() {}.type
            }
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

}