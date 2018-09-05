package com.otitan.data.remote

/**
 * Created by hanyw on 2018/8/10
 */

open class TitanSubscriber<T> : ErrorSubscriber<T>() {
    override fun onCompleted() {

    }

    override fun onError(ex: ExceptionEngine.ApiException) {

    }

    override fun onNext(t: T) {

    }
}


