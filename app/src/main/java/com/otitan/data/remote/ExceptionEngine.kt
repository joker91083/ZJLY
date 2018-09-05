package com.otitan.data.remote

import com.google.gson.JsonParseException

import org.json.JSONException

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

import retrofit2.adapter.rxjava.HttpException

/**
 * Created by hanyw on 2018/8/10
 */

object ExceptionEngine {
    //对应HTTP的状态码
    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {//HTTP错误
            ex = ApiException(e, TitanError.HTTP_ERROR)
            when (e.code()) {
                UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT -> ex.message = "服务请求超时，请稍后重试。"
                GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex.message = "服务端异常，请与管理员联系"
                else -> ex.message = "网络错误"  //均视为网络错误
            }
            return ex
        } else if (e is SocketTimeoutException) {
            ex = ApiException(e, TitanError.NETWORD_ERROR)
            ex.message = "连接超时，请检查网络连接或稍后重试"  //均视为网络错误
            return ex
        } else if (e is ServerException) {    //服务器返回的错误
            ex = ApiException(e, e.code)
            ex.message = e.message
            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            ex = ApiException(e, TitanError.PARSE_ERROR)
            ex.message = "解析错误"            //均视为解析错误
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(e, TitanError.NETWORD_ERROR)
            ex.message = "连接失败"  //均视为网络错误
            return ex
        } else {
            ex = ApiException(e, TitanError.UNKNOWN)
            ex.message = "未知错误"   //未知错误
            return ex
        }
    }


    class ApiException(throwable: Throwable, var code: Int) : Exception(throwable) {
        override var message: String? = null


    }

    class ServerException : RuntimeException() {
        var code: Int = 0
        override var message: String? = null
    }
}
