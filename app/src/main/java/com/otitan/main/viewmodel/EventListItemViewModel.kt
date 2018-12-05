package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.google.gson.Gson
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.local.LocalDataSource
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.EventModel
import com.otitan.ui.mview.IEventList
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import java.util.*

/**
 * 事件列表
 */
class EventListItemViewModel() : BaseViewModel() {

    val event = ObservableField<EventModel.EventResult>()
    val type = ObservableInt(1)
    val dataRepository = Injection.provideDataRepository()
    var mView: IEventList? = null

    constructor(context: Context?, mView: IEventList?) : this() {
        this.mContext = context
        this.mView = mView
    }

    fun itemOnClick() {

    }

    fun upEvent() {
        var auth = TitanApplication.sharedPreferences.getString("auth", null)
        if (auth.isNullOrBlank()) {
            mContext?.toast("登录信息验证失败")
            return
        }
        auth = "Bearer $auth"
        showDialog("上传中...")
        dataRepository.upEvent(auth, getBody(), object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                dismissDialog()
                mContext?.toast("事件上报失败:$info")
            }

            override fun onSuccess(result: Any) {
                dismissDialog()
                mContext?.toast(result.toString())
                delEvent()
            }
        })
    }

    fun getBody(): RequestBody {
        val list = ArrayList<EventModel.Att>()
//        picList.forEach {
//            val file = File(it)
//            val pic = EventModel.Att()
//            pic.FJLX = 1
//            pic.WJM = file.name
//            pic.WZWJM = it
//        }
//        if (audioPath.isNotEmpty()){
//


//        }
        val event = this.event.get()
        val upEvent = EventModel()
        when (event?.SJLX) {
            "森林火灾" -> upEvent.SJLX = 0
            "森林病虫害" -> upEvent.SJLX = 1
            "偷拉盗运" -> upEvent.SJLX = 2
            "乱砍滥伐" -> upEvent.SJLX = 3
            "征占用林地" -> upEvent.SJLX = 4
            "捕杀野生动物" -> upEvent.SJLX = 5
        }
        upEvent.LON = event?.LON.toString()
        upEvent.LAT = event?.LAT.toString()
        upEvent.SJMS = event?.SJMS.toString()
        upEvent.FSD = event?.FSD.toString()
        upEvent.LXFS = event?.LXFS.toString()
        upEvent.BZ = event?.BZ.toString()
        val gson = Gson()
        val json = gson.toJson(upEvent)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
    }

    fun delEvent() {
        event.get()?.locId?.let {
            dataRepository.delEvent(it, object : LocalDataSource.Callback {
                override fun onFailure(info: String) {
                    mContext?.toast(info)
                }

                override fun onSuccess(result: Any?) {
                    mView?.getNewData()
                }
            })
        }
    }
}