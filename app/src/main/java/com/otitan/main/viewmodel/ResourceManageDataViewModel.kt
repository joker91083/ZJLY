package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableInt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.IDataBase
import com.otitan.util.ToastUtil
import org.jetbrains.anko.toast

class ResourceManageDataViewModel() : BaseViewModel() {

    var data: ResultModel<Any>? = null
    //请求返回列表数据集合
    val items = ArrayList<Any>()
    val dataRepository = Injection.provideDataRepository()
    var mView: IDataBase? = null
    var type = ObservableInt(1)
    var year = 2009
    var dqcode = "330000"

    constructor(context: Context?, mView: IDataBase) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        getData(type.get(), year, dqcode)
    }

    fun getData(type: Int, year: Int, dqcode: String) {
        var auth = TitanApplication.loginResult?.access_token
        if (auth == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("加载中...")
        dataRepository.resourceManageData(auth, type, dqcode, year,
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<Any>
                        if (data?.data == null || (data?.data!! as List<Any>).size == 0) {
                            mContext?.toast("没有数据")
                        }
                        val typeToken = when (type) {
                            1 -> object : TypeToken<List<ResourceModel.LDMJModel>>() {}.type
                            2 -> object : TypeToken<List<ResourceModel.SLMJModel>>() {}.type
                            3 -> object : TypeToken<List<ResourceModel.GYLMJModel>>() {}.type
                            4 -> object : TypeToken<List<ResourceModel.SLFGLModel>>() {}.type
                            else -> object : TypeToken<List<ResourceModel.HLMXJModel>>() {}.type
                        }
                        val gson = Gson()
                        val json = gson.toJson(data?.data)
                        items.clear()
                        items.addAll(gson.fromJson(json, typeToken))
                        mView?.refresh()
                    }
                })
    }

    fun showPicker() {
        mView?.showPicker()
    }
}