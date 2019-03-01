package com.otitan.data.remote

import com.otitan.model.*
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable
import java.util.*


/**
 * Created by hanyw on 2018/8/17
 * Retrofit 接口
 */

interface RetrofitService {

    /**
     * 轨迹上传 没用这个
     */
//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("/api/track")
    fun addPointToServer(@Header("Authorization") auth: String, @Field("SBH") sbh: String, @Field("LON") lon: String, @Field("LAT") lat: String): Observable<ResultModel<Any>>

    /**
     * 登录
     */
//    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @GET("api/account/login")
    fun login(@Query("username") username: String, @Query("password") password: String, @Query("applicationName") applicationName: String): Observable<ResultModel<LoginResult>>
//    fun login(@Body body: RequestBody): Observable<LoginResult>

    /**
     * 设备信息注册
     */
    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @POST("/api/mobile")
    fun registerMobile(@Header("Authorization") auth: String, @Body body: RequestBody): Observable<ResultModel<Any>>
//    fun registerMobile(@Header("Content-Type") type: String, @Header("Authorization") auth: String, @Field("sbh") sbh: String): Observable<ResultModel<Any>>

    /**
     * 事件上传
     */
    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @POST("/api/event")
    fun upEvent(@Header("Authorization") auth: String, @Body body: RequestBody): Observable<ResultModel<Any>>

    /**
     * 轨迹上传 用的这个
     */
    @Headers("Content-Type: application/json")
    @POST("/api/track")
    fun upTrack(@Header("Authorization") auth: String, @Body body: RequestBody): Observable<ResultModel<Any>>

    /**
     * 事件
     */
    @GET("/api/event")
    fun eventList(@Header("Authorization") auth: String, @Query("fromTime") fromTime: String, @Query("toTime") toTime: String): Observable<ResultModel<Any>>

    /**------------- 语音识别 -----------------*/

    /**
     * 语音识别
     */
    @GET("/api/speech/GetResult")
    fun speech(@Query("phrase") phrase: String): Observable<ResultModel<Any>>

    /**------------- 指尖决策 -----------------*/

    /**
     * 决策信息订阅 type  1：新闻动态 2：绿化建设 3：有害生物防治 4：森林公安  5：林权制度改革
     */
    @GET("/api/mobileinfo/subscription")
    fun subscription(@Query("type") type: Int): Observable<ResultModel<List<LydtModel>>>

    /**------------- 林情概览 -----------------*/
    /**
     * 林业动态
     */
    @GET("/api/mobileinfo/forestydynamic")
    fun forestydynamic(): Observable<ResultModel<List<LydtModel>>>

    /**
     * 资源管护
     * [type]1：林地面积，2：森林面积，3：公益林面积，4：森林覆盖率，5：活立木蓄积
     */
    @GET("/api/resource/overview")
    fun resourceManage(@Header("Authorization") auth: String, @Query("type") type: Int,
                       @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 资源管护-数据管理
     * [type]1：林地面积，2：森林面积，3：公益林面积，4：森林覆盖率，5：活立木蓄积
     */
    @GET("/api/resource/datamanage")
    fun resourceManageData(@Header("Authorization") auth: String, @Query("type") type: Int,
                           @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 森林防火
     * [type] 1：火灾次数，2：火场面积，3：损失面积，4：扑火经费，5：人员伤亡
     */
    @GET("/api/fire/overview")
    fun slfh(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 森林防火-数据管理
     * [type] 1：火灾次数，2：火场面积，3：损失面积，4：扑火经费，5：人员伤亡
     */
    @GET("/api/fire/datamanage")
    fun slfhData(@Header("Authorization") auth: String, @Query("type") type: Int,
                 @Query("dqcode") dqcode: String, @Query("year") year: Int,
                 @Query("page") page: Int, @Query("size") size: Int,
                 @Query("keyword") keyword: String): Observable<ResultModel<SlfhModel<SlfhModel.Sjgl>>>

    /**
     * 行政执法
     */
    @GET("/api/law/overview")
    fun xzzf(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
             @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 行政执法 数据管理
     */
    @GET("/api/law/datamanage")
    fun xzzfData(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
                 @Query("year") year: Int, @Query("page") page: Int,
                 @Query("size") size: Int, @Query("keyword") keyword: String): Observable<ResultModel<XzzfModel<XzzfModel.Sjgl>>>

    /**
     * 林地征占
     * [type] 1：项目个数，2：征占用林地面积，3：林地定额
     */
    @GET("/api/occupy/overview")
    fun ldzz(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 林地征占 数据管理
     */
    @GET("/api/occupy/datamanage")
    fun ldzzData(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
                 @Query("year") year: Int, @Query("page") page: Int,
                 @Query("size") size: Int, @Query("keyword") keyword: String): Observable<ResultModel<LdzzModel<LdzzModel.Sjgl>>>

    /**
     * 营造林
     * [type] 1：完成情况-造林面积，2：完成情况-人工造林面积，3：完成情况-迹地更新面积，4：完成情况-森林抚育，
     * 5：完成情况-年末实有封山（沙）育林面积，6：完成情况-四旁（零星）植树，7：完成情况-当年苗木产量，
     * 8：完成情况-育苗面积，9：下达计划-造林面积，10：下达计划-人工造林面积，11：下达计划-封山育林，
     * 12：下达计划-森林抚育，13：下达计划-低效林改造，14：下达计划-当年新育苗面积，15：下达计划-四旁树
     */
    @GET("/api/afforestation/overview")
    fun yzl(@Header("Authorization") auth: String, @Query("type") type: Int,
            @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<List<YzlModel>>>

    /**
     * 营造林 数据管理
     * [type] 1：完成情况，2：计划下达
     */
    @GET("/api/afforestation/datamanage")
    fun yzlData(@Header("Authorization") auth: String, @Query("type") type: Int,
                @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<List<Any>>>

    /**
     * 有害生物
     * [type] 1：病害总计，2：虫害总计
     */
    @GET("/api/pest/overview")
    fun yhsw(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 有害生物 数据管理
     */
    @GET("/api/pest/datamanage")
    fun yhswData(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
                 @Query("year") year: Int, @Query("page") page: Int,
                 @Query("size") size: Int): Observable<ResultModel<YhswModel<Any>>>

    /**
     * 国有林场
     */
    @GET("/api/farm/overview")
    fun gylc(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String): Observable<ResultModel<Any>>

    /**
     * 国有林场 数据管理
     */
    @GET("/api/farm/datamanage")
    fun gylcData(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
                 @Query("page") page: Int, @Query("size") size: Int,
                 @Query("keyword") keyword: String): Observable<ResultModel<GylcModel<Any>>>

    /**
     * 森林公园
     */
    @GET("/api/park/overview")
    fun slgy(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String): Observable<ResultModel<Any>>

    /**
     * 森林公园 数据管理
     */
    @GET("/api/park/datamanage")
    fun slgyData(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
                 @Query("page") page: Int, @Query("size") size: Int,
                 @Query("keyword") keyword: String): Observable<ResultModel<SlgyModel<Any>>>

    /**
     * 湿地保护
     */
    @GET("/api/wetland/overview")
    fun sdbh(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String): Observable<ResultModel<Any>>

    /**
     * 湿地保护 数据管理
     */
    @GET("/api/wetland/datamanage")
    fun sdbhData(@Header("Authorization") auth: String, @Query("dqcode") dqcode: String,
                 @Query("page") page: Int, @Query("size") size: Int,
                 @Query("keyword") keyword: String): Observable<ResultModel<SdbhModel<Any>>>

    /**
     * 林业科技
     * 1：省院合作项目，2：示范资金项目，3：科技标准-国家标准，4：科技标准-行业标准，5：科技标准-省级地方标准，
     * 6：科技标准-浙江制造标准
     */
    @GET("/api/project/datamanage")
    fun lykj(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("page") page: Int, @Query("size") size: Int,
             @Query("keyword") keyword: String): Observable<ResultModel<LykjModel<Any>>>

    /**
     * 林业产业
     * [type] 1：总产值，2：第一产业，3：第二产业，4：第三产业
     */
    @GET("/api/industry/overview")
    fun lycy(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 林业产业 数据管理
     * [type] 1：总产值，2：第一产业，3：第二产业，4：第三产业
     */
    @GET("/api/industry/datamanage")
    fun lycyData(@Header("Authorization") auth: String, @Query("type") type: Int,
                 @Query("dqcode") dqcode: String, @Query("year") year: Int,
                 @Query("page") page: Int, @Query("size") size: Int): Observable<ResultModel<LycyModel<Any>>>

    /**
     * 林权
     * [type] 1:林权证本表，2:森林、林木、林地状况登记表
     */
    @GET("/api/warrant/overview")
    fun lq(@Header("Authorization") auth: String, @Query("type") type: Int,
           @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 林权 数据管理
     */
    @GET("/api/warrant/datamanage")
    fun lqData(@Header("Authorization") auth: String, @Query("type") type: Int,
               @Query("dqcode") dqcode: String, @Query("year") year: Int,
               @Query("page") page: Int, @Query("size") size: Int,
               @Query("keyword") keyword: String): Observable<ResultModel<LQuanModel<Any>>>

    /**
     * 植物检疫
     * [type] 1:国内调运检疫报检单，2:植物检疫证书信息，3:产地检疫报检,4:产地检疫合格证
     */
    @GET("/api/anti/overview")
    fun zwjy(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int): Observable<ResultModel<Any>>

    /**
     * 植物检疫 数据管理
     */
    @GET("/api/anti/datamanage")
    fun zwjyData(@Header("Authorization") auth: String, @Query("type") type: Int,
                 @Query("dqcode") dqcode: String, @Query("year") year: Int,
                 @Query("page") page: Int, @Query("size") size: Int,
                 @Query("keyword") keyword: String): Observable<ResultModel<ZwjyModel<Any>>>

    /**
     * 采伐运输
     * [type] 1:林木采伐证信息，2:木材运输证信息
     * [searchtype] 仅当type==1时 1:项目个数，2:采伐面积，3:采伐蓄积,4:采伐株数
     */
    @GET("/api/transport/overview")
    fun cfys(@Header("Authorization") auth: String, @Query("type") type: Int,
             @Query("dqcode") dqcode: String, @Query("year") year: Int,
             @Query("searchtype") searchtype: Int): Observable<ResultModel<Any>>

    /**
     * 采伐运输 数据管理
     */
    @GET("/api/transport/datamanage")
    fun cfysData(@Header("Authorization") auth: String, @Query("type") type: Int,
                 @Query("dqcode") dqcode: String, @Query("year") year: Int,
                 @Query("page") page: Int, @Query("size") size: Int,
                 @Query("keyword") keyword: String): Observable<ResultModel<CfysModel<Any>>>


}
