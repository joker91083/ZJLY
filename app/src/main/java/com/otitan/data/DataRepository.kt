package com.otitan.data

import com.otitan.TitanApplication
import com.otitan.base.ValueCallBack
import com.otitan.data.local.LocalDataSource
import com.otitan.data.local.LocalDataSourceImpl
import com.otitan.data.remote.RemoteDataSource
import com.otitan.data.remote.RemoteDataSourceImpl
import com.otitan.model.EventModel
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

    override fun saveEvent(eventModel: EventModel, callback: LocalDataSource.Callback) {
        localDataSource.saveEvent(eventModel, callback)
    }

    override fun delEvent(id: Long, callback: LocalDataSource.Callback) {
        localDataSource.delEvent(id, callback)
    }

    override fun addPointToServer(auth: String, lon: String, lat: String, sbh: String, callback: ValueCallBack<Any>) {
        return mRemoteDataSource.addPointToServer(auth, lon, lat, sbh, callback)
    }

    override fun queryTrackPoint(stratime: String, endtime: String, callback: LocalDataSource.Callback) {
        localDataSource.queryTrackPoint(stratime, endtime, callback)
    }

    override fun queryPOI(name: String, callback: LocalDataSource.Callback) {
        localDataSource.queryPOI(name, callback)
    }

    override fun queryEvent(callback: LocalDataSource.Callback) {
        localDataSource.queryEvent(callback)
    }

    override fun login(username: String, password: String, type: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.login(username, password, type, callback)
    }

    override fun registerMobile(auth: String, sbh: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.registerMobile(auth, sbh, callback)
    }

    override fun upEvent(auth: String, body: RequestBody, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.upEvent(auth, body, callback)
    }

    override fun upTrack(auth: String, body: RequestBody, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.upTrack(auth, body, callback)
    }

    override fun eventList(auth: String, fromTime: String, toTime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.eventList(auth, fromTime, toTime, callback)
    }

    override fun forestydynamic(callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.forestydynamic(callback)
    }

    override fun subscription(type: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.subscription(type, callback)
    }

    override fun resourceManage(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.resourceManage(auth, type, dqcode, year, callback)
    }

    override fun resourceManageData(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.resourceManageData(auth, type, dqcode, year, callback)
    }

    override fun slfh(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.slfh(auth, type, dqcode, year, callback)
    }

    override fun slfhData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.slfhData(auth, type, dqcode, year, page, size, keyword, callback)
    }

    override fun xzzf(auth: String, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.xzzf(auth, dqcode, year, callback)
    }

    override fun xzzfData(auth: String, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.xzzfData(auth, dqcode, year, page, size, keyword, callback)
    }

    override fun ldzz(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.ldzz(auth, type, dqcode, year, callback)
    }

    override fun ldzzData(auth: String, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.ldzzData(auth, dqcode, year, page, size, keyword, callback)
    }

    override fun yzl(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.yzl(auth, type, dqcode, year, callback)
    }

    override fun yzlData(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.yzlData(auth, type, dqcode, year, callback)
    }

    override fun yhsw(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.yhsw(auth, type, dqcode, year, callback)
    }

    override fun yhswData(auth: String, dqcode: String, year: Int, page: Int, size: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.yhswData(auth, dqcode, year, page, size, callback)
    }

    override fun gylc(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.gylc(auth, dqcode, callback)
    }

    override fun gylcData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.gylcData(auth, dqcode, page, size, keyword, callback)
    }

    override fun slgy(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.slgy(auth, dqcode, callback)
    }

    override fun slgyData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.slgyData(auth, dqcode, page, size, keyword, callback)
    }

    override fun sdbh(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.sdbh(auth, dqcode, callback)
    }

    override fun sdbhData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.sdbhData(auth, dqcode, page, size, keyword, callback)
    }

    override fun lykj(auth: String, type: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lykj(auth, type, page, size, keyword, callback)
    }

    override fun lycy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lycy(auth, type, dqcode, year, callback)
    }

    override fun lycyData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lycyData(auth, type, dqcode, year, page, size, callback)
    }

    override fun lq(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lq(auth, type, dqcode, year, callback)
    }

    override fun lqData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.lqData(auth, type, dqcode, year, page, size, keyword, callback)
    }

    override fun zwjy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.zwjy(auth, type, dqcode, year, callback)
    }

    override fun zwjyData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.zwjyData(auth, type, dqcode, year, page, size, keyword, callback)
    }

    override fun cfys(auth: String, type: Int, dqcode: String, year: Int, searchtype: Int, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.cfys(auth, type, dqcode, year, searchtype, callback)
    }

    override fun cfysData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.cfysData(auth, type, dqcode, year, page, size, keyword, callback)
    }
}
