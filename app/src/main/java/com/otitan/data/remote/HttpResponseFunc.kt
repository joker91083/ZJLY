package com.otitan.data.remote

import rx.Observable
import rx.functions.Func1

/**
 * Created by hanyw on 2018/8/10
 */

internal class HttpResponseFunc<T> : Func1<Throwable, Observable<T>> {
    override fun call(throwable: Throwable): Observable<T> {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(ExceptionEngine.handleException(throwable))
    }
}
