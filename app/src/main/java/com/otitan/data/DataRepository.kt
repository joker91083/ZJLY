package com.otitan.data

import com.otitan.TitanApplication
import com.otitan.base.ValueCallBack
import com.otitan.data.local.LocalDataSource
import com.otitan.data.local.LocalDataSourceImpl
import com.otitan.data.remote.RemoteDataSource
import com.otitan.data.remote.RemoteDataSourceImpl
import java.util.*

/**
 * Created by hanyw on 2018/8/10
 * 数据访问层
 */

class DataRepository private constructor(private val localDataSource: LocalDataSourceImpl, private val mRemoteDataSource: RemoteDataSourceImpl) : RemoteDataSource,LocalDataSource {


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


    override fun addLocalPoint(lon:String,lat:String,sbh:String,state:String) {
        localDataSource.addLocalPoint(lon,lat,sbh,state)
    }


    override fun addPointToServer(lon: String, lat: String, sbh: String,callback:ValueCallBack<Any>) {
        return mRemoteDataSource.addPointToServer(lon,lat,sbh,callback)
    }

    override fun queryTrackPoint(stratime: String, endtime: String, callback: LocalDataSource.Callback) {
        localDataSource.queryTrackPoint(stratime, endtime, callback)
    }

}
