package com.otitan.base

/**
 * Created by otitan_li on 2018/7/20.
 * ValueCallBack
 */

interface ValueCallBack<T> {

    fun onSuccess(t: T)

    fun onFail(code: String)
}
