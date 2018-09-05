package com.otitan.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by hanyw on 2018/8/17
 * Retrofit 初始化
 */
class RetrofitHelper() {
    private var mRetrofit: Retrofit? = null
    private var mWeather: Retrofit? = null

    /**
     * 常规接口
     * @return
     */
    val server: RetrofitService
        get() = mRetrofit!!.create(RetrofitService::class.java)

    val wServer: RetrofitService
        get() = mWeather!!.create(RetrofitService::class.java)

    init {
        resetApp()
    }

    private fun resetApp() {
        val okHttpClientBuilder = OkHttpClient.Builder()
        //5秒超时
        okHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        mRetrofit = Retrofit.Builder()
                .baseUrl(SERVER_HOST)
                //设置OKHttpClient
                .client(okHttpClientBuilder.build())
                //Xml转换器
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //Rx
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        mWeather = Retrofit.Builder()
                .baseUrl(HOST_ZHWNL)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }


    private object Holder {
        val single = RetrofitHelper()
    }

    companion object {
        //超时时间
        const val DEFAULT_TIMEOUT = 20L
        //接口地址
        const val SERVER_HOST = "http://sys.gyforest.com:8020/EcolMonitorManage/EcolWebService.asmx/"
        const val HOST_ZHWNL = "http://zhwnlapi.etouch.cn/Ecalender/api/v2/"
        val instance: RetrofitHelper by lazy { Holder.single }
    }
}

