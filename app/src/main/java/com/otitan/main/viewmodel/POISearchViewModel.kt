package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.view.inputmethod.InputMethodManager
import com.otitan.base.BaseViewModel
import com.otitan.data.DataRepository
import com.otitan.data.Injection
import com.otitan.data.local.LocalDataSource
import com.otitan.main.view.MapCenterActivity
import com.otitan.model.POIModel
import com.otitan.ui.mview.IPOISearch
import org.greenrobot.greendao.AbstractDaoMaster
import org.jetbrains.anko.toast

class POISearchViewModel() : BaseViewModel() {

    val hasData = ObservableBoolean(false)
    val isVisible = ObservableBoolean(false)
    var items = ArrayList<POIModel>()
    var name: String = ""
    val dataRepository = Injection.provideDataRepository()
    var mView: IPOISearch? = null

    constructor(context: Context?, mView: IPOISearch) : this() {
        mContext = context
        this.mView = mView
    }

    fun search() {
        if (name.isBlank()) {
            mContext?.toast("请输入要搜索的名称")
            return
        }
        dataRepository.queryPOI(name, object : LocalDataSource.Callback {
            override fun onFailure(info: String) {
                mContext?.toast(info)
                items.clear()
                hasData.set(false)
                mView?.refresh()
            }

            override fun onSuccess(result: Any?) {
                items = result as ArrayList<POIModel>

                mView?.refresh()
            }
        })
    }

    fun close() {
        val imm = mContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow((mContext as MapCenterActivity?)?.currentFocus?.windowToken, 0)
        isVisible.set(false)
        mView?.close()
    }

}
