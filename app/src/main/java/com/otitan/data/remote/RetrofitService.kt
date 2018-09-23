package com.otitan.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by hanyw on 2018/8/17
 * Retrofit 接口
 */

interface RetrofitService {

    @GET("")
    fun getWeather(@Query("app") app: String, @Query("weaid") weaid: String,
                   @Query("appkey") appkey: String, @Query("sign") sign: String,
                   @Query("format") format: String): Observable<String>
    @GET("addPoint")
    fun addPointToServer(@Query("lon")lon:String,@Query("lat")lat:String,@Query("sbh")sbh:String):Observable<String>



}
