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
import com.otitan.model.LykjModel
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.ILykj
import com.otitan.ui.mview.IResourceManage
import com.otitan.util.ToastUtil
import com.otitan.util.Utils
import com.otitan.zjly.R
import org.jetbrains.anko.toast

class LykjViewModel() : BaseViewModel() {

    var data: ResultModel<LykjModel<Any>>? = null
    val keyList = ArrayList<String>()
    val valueList = ArrayList<String>()
    var dqName = "浙江省"
    val dataRepository = Injection.provideDataRepository()
    var mView: ILykj? = null
    val barChartDataList = ArrayList<BarEntry>()
    val isKjbz = ObservableBoolean(false)
    var type = 1

    constructor(context: Context?, mView: ILykj) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        getData(type)
    }

    fun getData(type: Int) {
        var auth = TitanApplication.loginResult?.access_token
        if (auth == null) {
            mContext?.toast("用户信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("加载中...")
        dataRepository.lykj(auth, type, 1, 10, "",
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<LykjModel<Any>>
                        if (data?.data == null || data?.data?.rows?.size == 0) {
                            mContext?.toast("没有数据")
                        }
                        mView?.refresh(data?.data?.rows)
                    }
                })
    }

}