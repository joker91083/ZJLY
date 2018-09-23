package com.otitan.data.remote

import com.otitan.base.ValueCallBack


/**
 * Created by hanyw on 2018/8/7
 */

interface RemoteDataSource {

    interface mCallback {

        fun onFailure(info: String)

        fun onSuccess(result: Any)
    }

    /**
     * 获取天气信息
     */
    fun getWeather(citykey: String,callback: RemoteDataSource.mCallback)

    /**
     * 上传轨迹数据
     */
    fun addPointToServer(lon:String, lat:String, sbh:String, callback: ValueCallBack<Any>)
}
