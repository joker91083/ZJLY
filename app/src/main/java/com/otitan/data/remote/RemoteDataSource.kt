package com.otitan.data.remote


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
}
