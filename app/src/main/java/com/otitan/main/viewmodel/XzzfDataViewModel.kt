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
import com.otitan.ui.mview.IDataBase
import com.otitan.util.ToastUtil
import org.jetbrains.anko.toast

class XzzfDataViewModel() : BaseViewModel() {

    var data: ResultModel<XzzfModel<XzzfModel.Sjgl>>? = null
    //请求返回列表数据集合
    val items = ArrayList<Any>()
    val dataRepository = Injection.provideDataRepository()
    val isFinishRefreshing = ObservableBoolean(false)
    val isFinishLoading = ObservableBoolean(false)
    val hasMore = ObservableBoolean(true)
    var mView: IDataBase? = null
    var year = 2009
    var page = 1
    var dqcode = "330000"
    var keyWord = ""

    constructor(context: Context?, mView: IDataBase) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
//        onRefresh.execute()
        mView?.startRefresh()
    }

    val onRefresh = BindingCommand(object : BindingAction {
        override fun call() {
            hasMore.set(true)
            page = 1
            getData(year, dqcode, 1)
        }
    })

    val onLoadMore = BindingCommand(object : BindingAction {
        override fun call() {
            getData(year, dqcode, 2)
        }
    })

    fun getData(year: Int, dqcode: String, requestCode: Int) {
        var auth = TitanApplication.loginResult?.access_token
        if (auth == null) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
//        showDialog("加载中...")
        dataRepository.xzzfData(auth, dqcode, year, page, 10, keyWord,
                object : RemoteDataSource.mCallback {
                    override fun onFailure(info: String) {
//                        dismissDialog()
                        when (requestCode) {
                            1 -> isFinishRefreshing.set(!isFinishRefreshing.get())
                            2 -> isFinishLoading.set(!isFinishLoading.get())
                        }
                        ToastUtil.setToast(mContext, info)
                    }

                    override fun onSuccess(result: Any) {
                        dismissDialog()
                        data = result as ResultModel<XzzfModel<XzzfModel.Sjgl>>
                        when (requestCode) {
                            1 -> {
                                items.clear()
                                isFinishRefreshing.set(!isFinishRefreshing.get())
                            }
                            2 -> {
                                isFinishLoading.set(!isFinishLoading.get())
                            }
                        }
                        if (data?.data == null || data?.data?.rows?.size == 0) {
                            mContext?.toast("没有数据")
                            hasMore.set(false)
                            return
                        }
                        data?.data?.rows?.let {
                            items.addAll(it)
                            mView?.refresh()
                            this@XzzfDataViewModel.page++
                        }
                    }
                })
    }

    fun showPicker() {
        mView?.showPicker()
    }
}