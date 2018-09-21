package com.otitan.data

import com.otitan.data.local.LocalDataSourceImpl
import com.otitan.data.remote.RemoteDataSource
import com.otitan.data.remote.RemoteDataSourceImpl

/**
 * Created by hanyw on 2018/8/10
 * 数据访问层
 */

class DataRepository private constructor(private val localDataSource: LocalDataSourceImpl, private val mRemoteDataSource: RemoteDataSourceImpl) : RemoteDataSource {

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(localDataSource: LocalDataSourceImpl, remoteDataSource: RemoteDataSourceImpl): DataRepository {
            if (INSTANCE == null) {
                synchronized(DataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DataRepository(localDataSource, remoteDataSource)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun getWeather(citykey: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getWeather(citykey, callback)
    }


}
