package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import com.otitan.base.BaseViewModel
import com.otitan.base.BindingAction
import com.otitan.base.BindingCommand
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.LydtModel
import com.otitan.ui.mview.INewList
import org.jetbrains.anko.toast

class NewListViewModel() : BaseViewModel() {

    val dataRepository = Injection.provideDataRepository()
    var mView: INewList? = null
    var type: Int = 1
    val isFinishRefreshing = ObservableBoolean(false)
    val isFinishLoading = ObservableBoolean(false)
    var data = ArrayList<LydtModel>()


    val onRefresh = BindingCommand(object : BindingAction {
        override fun call() {
            getData()
        }
    })

    val onLoadMore = BindingCommand(object : BindingAction {
        override fun call() {
//            getData(2)
        }
    })

    constructor(context: Context?, mView: INewList) : this() {
        this.mContext = context
        this.mView = mView
    }

    override fun onCreate() {
        super.onCreate()
        onRefresh.execute()
    }

    fun getData() {
        dataRepository.subscription(type, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                mContext?.toast(info)
                isFinishRefreshing.set(!isFinishRefreshing.get())
            }

            override fun onSuccess(result: Any) {
                data.clear()
                data.addAll(result as List<LydtModel>)
                isFinishRefreshing.set(!isFinishRefreshing.get())
                mView?.refresh()
            }
        })
    }
}