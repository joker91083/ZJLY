package com.otitan.data.remote

import com.otitan.base.ValueCallBack
import com.otitan.model.LoginInfo
import okhttp3.RequestBody


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
    fun addPointToServer(lon: String, lat: String, sbh: String, callback: ValueCallBack<Any>)

    /**
     * 登录
     */
    fun login(username: String, password: String, type: String, callback: RemoteDataSource.mCallback)

    /**
     * 资源管护
     */
    fun resourceManage(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 森林防火
     */
    fun slfh(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 行政执法
     */
    fun xzzf(auth: String, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 营造林
     */
    fun yzl(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 林业产业
     */
    fun lycy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 林地征占
     */
    fun ldzz(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 有害生物
     */
    fun yhsw(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback)

    /**
     * 国有林场
     */
    fun gylc(auth: String, dqcode: String, callback: RemoteDataSource.mCallback)

    /**
     * 湿地保护
     */
    fun sdbh(auth: String, dqcode: String, callback: RemoteDataSource.mCallback)

    /**
     * 森林公园
     */
    fun slgy(auth: String, dqcode: String, callback: RemoteDataSource.mCallback)

    /**
     * 林业科技
     */
    fun lykj(auth: String, type: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback)
}
