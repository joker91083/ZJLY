package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.base.BindingAction
import com.otitan.base.BindingCommand
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.ResultModel
import com.otitan.model.SlfhModel
import com.otitan.model.XzzfModel
import com.otitan.model.YzlModel
import com.otitan.ui.mview.IDataBase
import com.otitan.util.ToastUtil
import org.jetbrains.anko.toast

class YzlDataViewModel() : BaseViewModel() {

    var data: ResultModel<List<Any>>? = null
    //请求返回列表数据集合
    val items = ArrayList<Any>()
    val dataRepository = Injection.provideDataRepository()
    var mView: IDataBase? = null
    var type = 1
    var year = 2011
    var page = 1
    var dqcode = "330000"

    constructor(context: Context?, mView: IDataBase) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        request()
    }

    fun request() {
        getData(type, year, dqcode)
    }

    fun getData(type: Int, year: Int, dqcode: String) {
        var auth = TitanApplication.loginResult?.access_token
        if (auth == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("加载中...")
        dataRepository.yzlData(auth, type, dqcode, year,
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
                        dismissDialog()
                        ToastUtil.setToast(mContext, info)
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<List<Any>>
                        if (data?.data == null || data?.data?.size == 0) {
                            mContext?.toast("没有数据")
                            return
                        }
                        data?.data?.let {
                            items.clear()
                            items.addAll(it)
                            mView?.refresh()
                            this@YzlDataViewModel.page++
                        }
                    }
                })
    }

    fun showPicker() {
        mView?.showPicker()
    }
}