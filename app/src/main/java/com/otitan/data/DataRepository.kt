package com.otitan.data

import com.otitan.TitanApplication
import com.otitan.base.ValueCallBack
import com.otitan.data.local.LocalDataSource
import com.otitan.data.local.LocalDataSourceImpl
import com.otitan.data.remote.RemoteDataSource
import com.otitan.data.remote.RemoteDataSourceImpl
import com.otitan.model.LoginInfo
import okhttp3.RequestBody
import java.util.*

/**
 * Created by hanyw on 2018/8/10
 * 数据访问层
 */

class DataRepository private constructor(private val localDataSource: LocalDataSourceImpl, private val mRemoteDataSource: RemoteDataSourceImpl) : RemoteDataSource, LocalDataSource {


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


    override fun addLocalPoint(lon: String, lat: String, sbh: String, state: String) {
        localDataSource.addLocalPoint(lon, lat, sbh, state)
    }


    override fun addPointToServer(lon: String, lat: String, sbh: String, callback: ValueCallBack<Any>) {
        return mRemoteDataSource.addPointToServer(lon, lat, sbh, callback)
    }

    override fun queryTrackPoint(stratime: String, endtime: String, callback: LocalDataSource.Callback) {
        localDataSource.queryTrackPoint(stratime, endtime, callback)
    }

    override fun login(username: String, password: String, type: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.login(username, password, type, callback)
    }

    override fun resourceManage(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.resourceManage(auth, type, dqcode, year, callback)
    }

    override fun slfh(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.slfh(auth, type, dqcode, year, callback)
    }

    override fun yzl(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.yzl(auth, type, dqcode, year, callback)
    }

    override fun xzzf(auth: String, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.xzzf(auth, dqcode, year, callback)
    }

    override fun yhsw(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.yhsw(auth, type, dqcode, year, callback)
    }

    override fun ldzz(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.ldzz(auth, type, dqcode, year, callback)
    }

    override fun lycy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lycy(auth, type, dqcode, year, callback)
    }

    override fun gylc(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.gylc(auth, dqcode, callback)
    }

    override fun sdbh(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.sdbh(auth, dqcode, callback)
    }

    override fun slgy(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.slgy(auth, dqcode, callback)
    }

    override fun lykj(auth: String, type: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lykj(auth, type, page, size, keyword, callback)
    }
}
