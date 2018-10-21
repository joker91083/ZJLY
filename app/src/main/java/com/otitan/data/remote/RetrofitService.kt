package com.otitan.data.remote

import com.otitan.model.*
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable


/**
 * Created by hanyw on 2018/8/17
 * Retrofit 接口
 */

interface RetrofitService {

    @GET("addPoint")
    fun addPointToServer(@Query("lon") lon: String, @Query("lat") lat: String, @Query("sbh") sbh: String): Observable<String>

    /**
     * 登录
     */
//    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @GET("api/account/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Observable<ResultModel<LoginResult>>
//    fun login(@Body body: RequestBody): Observable<LoginResult>

    /**
     * 设备信息注册
     */
    @FormUrlEncoded
    @POST("/api/mobile")
    fun registerMoblie(@Header("Authorization") auth: String, @Field("sbh") sbh: String): Observable<ResultModel<Any>>

    /**
     * 事件上传
     */
    @FormUrlEncoded
    @POST("/api/event")
    fun upEvent(): Observable<ResultModel<Any>>

    /**
     * 资源管护
     * [type]1：林地面积，2：森林面积，3：公益林面积，4：森林覆盖率，5：活立木蓄积
     */
    @GET("/api/resource/overview")
    fun resourceManage(@Header("Authorization") auth: String, @Query("type") type: Int,
                       @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 林地征占
     * [type] 1：项目个数，2：征占用林地面积，3：林地定额
     */
    @GET("/api/occupy/overview")
    fun ldzz(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 有害生物
     * [type] 1：病害总计，2：虫害总计
     */
    @GET("/api/pest/overview")
    fun yhsw(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 森林防火
     * [type] 1：火灾次数，2：火场面积，3：损失面积，4：扑火经费，5：人员伤亡
     */
    @GET("/api/fire/overview")
    fun slfh(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 林业产业
     * [type] 1：总产值，2：第一产业，3：第二产业，4：第三产业
     */
    @GET("/api/industry/overview")
    fun lycy(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 营造林
     * [type] 1：完成情况-造林面积，2：完成情况-人工造林面积，3：完成情况-迹地更新面积，4：完成情况-森林抚育，
     * 5：完成情况-年末实有封山（沙）育林面积，6：完成情况-四旁（零星）植树，7：完成情况-当年苗木产量，
     * 8：完成情况-育苗面积，9：下达计划-造林面积，10：下达计划-人工造林面积，11：下达计划-封山育林，
     * 12：下达计划-森林抚育，13：下达计划-低效林改造，14：下达计划-当年新育苗面积，15：下达计划-四旁树
     */
    @GET("/api/afforestation/overview")
    fun yzl(@Header("Authorization") auth: String, @Query("type") type: Int,
            @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 行政执法
     */
    @GET("/api/law/overview")
    fun xzzf(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
             @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 国有林场
     */
    @GET("/api/farm/overview")
    fun gylc(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String): Observable<ResultModel<Any>>

    /**
     * 湿地保护
     */
    @GET("/api/wetland/overview")
    fun sdbh(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String): Observable<ResultModel<Any>>

    /**
     * 森林公园
     */
    @GET("/api/park/overview")
    fun slgy(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String): Observable<ResultModel<Any>>

    /**
     * 林业科技
     * 1：省院合作项目，2：示范资金项目，3：科技标准-国家标准，4：科技标准-行业标准，5：科技标准-省级地方标准，
     * 6：科技标准-浙江制造标准
     */
    @GET("/api/project/datamanage")
    fun lykj(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("page") page: Int, @Query("size") size: Int,
             @Query("keyword") keyword: String): Observable<ResultModel<LykjModel<Any>>>
}
