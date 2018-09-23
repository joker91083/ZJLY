package com.otitan.data.remote

import com.otitan.base.ValueCallBack
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by hanyw on 2018/8/18
 * 后段数据接口实现
 */

class RemoteDataSourceImpl() : RemoteDataSource {


    private object Holder {
        val single = RemoteDataSourceImpl()
    }

    companion object {
        val instance: RemoteDataSourceImpl by lazy { Holder.single }
//        private var remoteDataSourceImpl: RemoteDataSourceImpl by Delegates.notNull()
//        fun getInstance(): RemoteDataSourceImpl {
//            remoteDataSourceImpl = RemoteDataSourceImpl()
////            if (remoteDataSourceImpl == null) {
////                remoteDataSourceImpl = RemoteDataSourceImpl(context)
////            }
//            return remoteDataSourceImpl as RemoteDataSourceImpl
//        }
    }


    override fun getWeather(citykey: String, callback: RemoteDataSource.mCallback) {

    }

    override fun addPointToServer(lon: String, lat: String, sbh: String,callback: ValueCallBack<Any>) {
        var observable = RetrofitHelper.instance.server.addPointToServer(lon,lat,sbh)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(s: String) {
                        callback.onSuccess(s)
                    }
                })

    }


}
