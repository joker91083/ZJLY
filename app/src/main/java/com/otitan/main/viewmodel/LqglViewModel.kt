package com.otitan.main.viewmodel

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.LydtModel
import com.otitan.ui.mview.ILQGL
import com.otitan.util.ToastUtil

class LqglViewModel() : BaseViewModel() {
    val dataRepository = Injection.provideDataRepository()

    var mView: ILQGL? = null
    var imgs = ArrayList<String>()
    var titles = ArrayList<String>()
    var data: List<LydtModel>? = null

    constructor(mContext: Context?, mView: ILQGL) : this() {
        this.mContext = mContext
        this.mView = mView
        initData()
    }

    fun initData() {
        dataRepository.forestydynamic(object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                ToastUtil.setToast(mContext, info)
            }

            override fun onSuccess(result: Any) {
                data = result as List<LydtModel>?
                data?.forEach {
                    imgs.add(it.imgurl)
                    titles.add(it.title)
                }
                mView?.refresh()
            }
        })
    }

}
