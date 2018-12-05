package com.otitan.data.remote

import com.otitan.base.ValueCallBack
import com.otitan.model.LoginInfo
import okhttp3.RequestBody
import java.util.*


/**
 * Created by hanyw on 2018/8/7
 */

interface RemoteDataSource {

    interface mCallback {

        fun onFailure(info: String)

        fun onSuccess(result: Any)
    }

    /**
     * 获取天气信息
     */
    fun getWeather(citykey: String, callback: RemoteDataSource.mCallback)

    /**
     * 上传轨迹数据
     */
    fun addPointToServer(auth: String, lon: String, lat: String, sbh: String, callback: ValueCallBack<Any>)

    /**
     * 登录
     */
    fun login(username: String, password: String, type: String, callback: RemoteDataSource.mCallback)

    /**
     * 设备信息注册
     */
    fun registerMobile(auth: String, sbh: String, callback: mCallback)

    /**
     * 事件上传
     */
    fun upEvent(auth: String, body: RequestBody, callback: mCallback)

    /**
     * 轨迹上传
     */
    fun upTrack(auth: String, body: RequestBody, callback: mCallback)

    /**
     * 事件列表
     */
    fun eventList(auth: String, fromTime: String, toTime: String, callback: mCallback)

    /**
     * 林业动态
     */
    fun forestydynamic(callback: mCallback)

    /**
     * 决策信息订阅
     */
    fun subscription(type: Int, callback: mCallback)

    /**
     * 资源管护
     */
    fun resourceManage(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 资源管护-数据管理
     */
    fun resourceManageData(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 森林防火
     */
    fun slfh(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 森林防火
     */
    fun slfhData(auth: String, type: Int, dqcode: String, year: Int,
                 page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 行政执法
     */
    fun xzzf(auth: String, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 行政执法 数据管理
     */
    fun xzzfData(auth: String, dqcode: String, year: Int, page: Int, size: Int,
                 keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 林地征占
     */
    fun ldzz(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 林地征占 数据管理
     */
    fun ldzzData(auth: String, dqcode: String, year: Int, page: Int, size: Int,
                 keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 营造林
     */
    fun yzl(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 营造林 数据管理
     */
    fun yzlData(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 有害生物
     */
    fun yhsw(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 有害生物 数据管理
     */
    fun yhswData(auth: String, dqcode: String, year: Int, page: Int, size: Int, callback: RemoteDataSource.mCallback)

    /**
     * 国有林场
     */
    fun gylc(auth: String, dqcode: String, callback: RemoteDataSource.mCallback)

    /**
     * 国有林场 数据管理
     */
    fun gylcData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 森林公园
     */
    fun slgy(auth: String, dqcode: String, callback: RemoteDataSource.mCallback)

    /**
     * 森林公园 数据管理
     */
    fun slgyData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 湿地保护
     */
    fun sdbh(auth: String, dqcode: String, callback: RemoteDataSource.mCallback)

    /**
     * 湿地保护 数据管理
     */
    fun sdbhData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 林业科技
     */
    fun lykj(auth: String, type: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 林业产业
     */
    fun lycy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 林业产业 数据管理
     */
    fun lycyData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, callback: RemoteDataSource.mCallback)

    /**
     * 林权
     */
    fun lq(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 林权 数据管理
     */
    fun lqData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int,
               keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 植物检疫
     */
    fun zwjy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 植物检疫 数据管理
     */
    fun zwjyData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int,
                 keyword: String, callback: RemoteDataSource.mCallback)

    /**
     * 采伐运输
     */
    fun cfys(auth: String, type: Int, dqcode: String, year: Int, searchtype: Int,
             callback: RemoteDataSource.mCallback)

    /**
     * 采伐运输 数据管理
     */
    fun cfysData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int,
                 keyword: String, callback: RemoteDataSource.mCallback)
}
