package com.otitan.data.remote

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
}