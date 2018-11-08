package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.google.gson.Gson
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.local.LocalDataSource
import com.otitan.data.remote.RemoteDataSource
import com.otitan.model.EventModel
import com.otitan.ui.mview.IUpEvent
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import java.io.File
import java.util.ArrayList

class UpEventViewModel() : BaseViewModel() {

    var mView: IUpEvent? = null
    val event = ObservableField<EventModel>(EventModel())
    val dataRepository = Injection.provideDataRepository()
    val picList = ArrayList<String>()
    var audioPath = ""
    var videoPath = ""

    constructor(context: Context?, mView: IUpEvent) : this() {
        this.mContext = context
        this.mView = mView
    }

    fun selectPic() {
        mView?.selectPic()
    }

    fun selectAudio() {
        mView?.selectAudio()
    }

    fun selectVideo() {
        mView?.selectVideo()
    }

    fun selectType() {
        mView?.selectType()
    }

    /**
     * 本地保存
     */
    fun locSave() {
        if (!checkInfo()) {
            mContext?.toast("请将内容填写完整")
            return
        }
        if (event.get() == null) {
            return
        }
        dataRepository.saveEvent(event.get()!!, object : LocalDataSource.Callback {
            override fun onFailure(info: String) {
                mContext?.toast(info)
            }

            override fun onSuccess(result: Any?) {
                mContext?.toast("保存成功")
            }
        })
    }

    /**
     * 在线上报
     */
    fun upEvent() {
        if (!checkInfo()) {
            mContext?.toast("请将内容填写完整")
            return
        }
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
        val gson = Gson()
        val json = gson.toJson(event)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
    }

    fun checkInfo(): Boolean {
        var result = true
        val event = this.event.get()
        when {
            event?.LON.isNullOrBlank() || event?.LAT.isNullOrBlank() ||
                    event?.SJMS.isNullOrBlank() || event?.FSD.isNullOrBlank() -> result = false
            event?.SJLX == null || event.SJLX == -1 -> result = false
        }
        return result
    }
}