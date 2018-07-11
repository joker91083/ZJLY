package com.otitan.zjly.base

import com.otitan.zjly.local.LocalDataSourceImpl
import com.otitan.zjly.remote.RemoteDataSourceImpl

/**
 * Created by sp on 2018/6/7.
 * 数据访问层
 */
class DataRepository private constructor(private val mLocalDataSource: LocalDataSourceImpl, private val mRemoteDataSource: RemoteDataSourceImpl) {

    companion object {
        private var INSTANCE: DataRepository? = null
        fun getInstance(localDataSource: LocalDataSourceImpl, remoteDataSource: RemoteDataSourceImpl): DataRepository {
            if (INSTANCE == null) {
                INSTANCE = DataRepository(localDataSource, remoteDataSource)
            }
            return INSTANCE as DataRepository
        }
    }
}
