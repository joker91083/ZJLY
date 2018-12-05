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
import com.otitan.model.LycyModel
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.ILycy
import com.otitan.ui.mview.IResourceManage
import com.otitan.util.ToastUtil
import com.otitan.util.Utils
import com.otitan.zjly.R
import org.jetbrains.anko.toast
import java.math.BigDecimal
import java.math.RoundingMode

class LycyViewModel() : BaseViewModel() {

    var data: ResultModel<List<LinkedTreeMap<String, Any>>>? = null
    val keyList = ArrayList<String>()
    val valueList = ArrayList<String>()
    val dqList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: ILycy? = null
    val hasData = ObservableBoolean(false)
    val barChartDataList = ArrayList<BarEntry>()
    var type = 1
    var year = 2015
    val arrayList = arrayOf(R.array.lycy1, R.array.lycy2, R.array.lycy3, R.array.lycy4)

    constructor(context: Context?, mView: ILycy) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        getData(type, year)
    }

    fun getData(type: Int, year: Int) {
        val info = TitanApplication.loginResult
        if (info?.access_token == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        val auth = "Bearer ${info.access_token}"
        showDialog("加载中...")
        dataRepository.lycy(auth, type, info.code ?: "", year,
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
            val array = mContext?.resources?.getStringArray(arrayList[type - 1])
            val map = HashMap<String, String>()
            array?.forEach {
                val values = it.split(",")
                map[values[0]] = values[1]
            }
            var i = 0f
            val gson = Gson()
            var zcy: LycyModel.Zcy? = null
            var dycy: LycyModel.Dycy? = null
            var decy: LycyModel.Decy? = null
            var dscy: LycyModel.Dscy? = null
            (data?.data as List<LinkedTreeMap<String, Any>>).forEach continuing@{
                if (it["Name"] == "浙江省") {
                    return@continuing
                }
                val json = gson.toJson(it)
                dqList.add(it["Name"].toString())
                when (type) {
                    1 -> {
                        zcy = gson.fromJson(json, LycyModel.Zcy::class.java)
                        barChartDataList.add(BarEntry(i, getTotal(type, zcy).toFloat()))
                    }
                    2 -> {
                        dycy = gson.fromJson(json, LycyModel.Dycy::class.java)
                        barChartDataList.add(BarEntry(i, getTotal(type, dycy).toFloat()))
                    }
                    3 -> {
                        decy = gson.fromJson(json, LycyModel.Decy::class.java)
                        barChartDataList.add(BarEntry(i, getTotal(type, decy).toFloat()))
                    }
                    4 -> {
                        dscy = gson.fromJson(json, LycyModel.Dscy::class.java)
                        barChartDataList.add(BarEntry(i, getTotal(type, dscy).toFloat()))
                    }
                }
                i++
            }
        }
    }

    fun conversionTableData(data: ResultModel<List<LinkedTreeMap<String, Any>>>?): List<Any> {
        val list = ArrayList<Any>()
        if (data != null) {
            val gson = Gson()
            val json = gson.toJson(data.data)

            val type = when (type) {
                1 -> object : TypeToken<List<LycyModel.Zcy>>() {}.type
                2 -> object : TypeToken<List<LycyModel.Dycy>>() {}.type
                3 -> object : TypeToken<List<LycyModel.Decy>>() {}.type
                else -> object : TypeToken<List<LycyModel.Dscy>>() {}.type
            }
            list.addAll(gson.fromJson(json, type))
        }
        return list
    }

    /**
     * 获取总产值
     */
    fun getTotal(type: Int?, obj: Any?): String {
        obj ?: return "0.00"
        return when (type) {
            1 -> {
                obj as LycyModel.Zcy
                String.format("%.2f", (obj.First + obj.Second + obj.Third))
            }
            2 -> {
                obj as LycyModel.Dycy
                String.format("%.2f", (obj.lmyzym + obj.yzl + obj.mczccy + obj.jjlcpzzcj
                        + obj.hhjqtgszwzz + obj.lsysdwfyly + obj.qt))
            }
            3 -> {
                obj as LycyModel.Decy
                String.format("%.2f", (obj.mztzwzpzz + obj.mztjjzz + obj.mzwjzzzp + obj.lchxcpzz
                        + obj.mzgypmzwjtyypzz + obj.fmzlcpjgzzy + obj.qt))
            }
            4 -> {
                obj as LycyModel.Dscy
                String.format("%.2f", (obj.lyscfw + obj.lylyyxxfw + obj.lystfw + obj.lyzyjsfw
                        + obj.lygggljqtzzfw + obj.qt))
            }
            else -> "0.00"
        }
    }
}