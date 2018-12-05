package com.otitan.data.remote

import android.util.Log
import com.google.gson.Gson
import com.otitan.base.ValueCallBack
import com.otitan.model.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.HttpException
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.*


/**
 * Created by hanyw on 2018/8/18
 * 后段数据接口实现
 */

class RemoteDataSourceImpl() : RemoteDataSource {


    private object Holder {
        val single = RemoteDataSourceImpl()
    }

    companion object {
        val instance: RemoteDataSourceImpl by lazy { Holder.single }
//        private var remoteDataSourceImpl: RemoteDataSourceImpl by Delegates.notNull()
//        fun getInstance(): RemoteDataSourceImpl {
//            remoteDataSourceImpl = RemoteDataSourceImpl()
////            if (remoteDataSourceImpl == null) {
////                remoteDataSourceImpl = RemoteDataSourceImpl(context)
////            }
//            return remoteDataSourceImpl as RemoteDataSourceImpl
//        }
    }


    override fun getWeather(citykey: String, callback: RemoteDataSource.mCallback) {

    }

    override fun addPointToServer(auth: String, lon: String, lat: String, sbh: String, callback: ValueCallBack<Any>) {
        val observable = RetrofitHelper.instance.server.addPointToServer(auth, lon, lat, sbh)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        Log.e("tag", "轨迹上传失败：" + getErrBody(e))
                    }

                    override fun onNext(s: ResultModel<Any>) {
                        callback.onSuccess(s)
                    }
                })

    }

    /**
     * 登录
     */
    override fun login(username: String, password: String, type: String, callback: RemoteDataSource.mCallback) {
        val loginInfo = LoginInfo()
        loginInfo.password = password
        loginInfo.username = username
        val gson = Gson()
        val body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), gson.toJson(loginInfo))
        val observable = RetrofitHelper.instance.server
                .login(username, password, "浙江省智慧林业云平台移动端")
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<LoginResult>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<LoginResult>?) {
                        when {
//                            t!=null->callback.onSuccess(t)
                            t?.responseMsg != "success" -> callback.onFailure(t?.responseMsg?.toString()!!)
                            t.data.token?.access_token != null -> callback.onSuccess(t)
                            else -> callback.onFailure("登录失败")
                        }
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 设备信息注册
     */
    override fun registerMobile(auth: String, sbh: String, callback: RemoteDataSource.mCallback) {
        val json = sbh
        val type = "application/json"
        val observable = RetrofitHelper.instance.server.registerMobile(auth, requestBody(json))
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        errInfo(e, callback)
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        t?.let {
                            callback.onSuccess(it.responseMsg == "success")
                        }
                    }

                    override fun onCompleted() {
                    }
                })
    }

    override fun upEvent(auth: String, body: RequestBody, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.upEvent(auth, body)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        errInfo(e, callback)
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        Log.e("tag", t?.responseMsg)
                        t?.let {
                            if (it.responseMsg == "success") {
                                callback.onSuccess("上传成功")
                            } else {
                                callback.onFailure("上传失败")
                            }
                        }
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 轨迹上传
     */
    override fun upTrack(auth: String, body: RequestBody, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.upTrack(auth, body)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        errInfo(e, callback)
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        Log.e("tag", t?.responseMsg)
                        t?.let {
                            if (it.responseMsg == "success") {
                                callback.onSuccess("true")
                            } else {
                                callback.onFailure("false")
                            }
                        }
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 事件列表
     */
    override fun eventList(auth: String, fromTime: String, toTime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.eventList(auth, fromTime, toTime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        errInfo(e, callback)
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林业动态
     */
    override fun forestydynamic(callback: RemoteDataSource.mCallback) {
        val observer = RetrofitHelper.instance.server.forestydynamic()
        observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<List<LydtModel>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<List<LydtModel>>?) {
                        if (t != null) {
                            callback.onSuccess(t.data)
                        } else {
                            callback.onFailure("林业动态获取失败")
                        }
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 决策信息订阅
     */
    override fun subscription(type: Int, callback: RemoteDataSource.mCallback) {
        val observer = RetrofitHelper.instance.server.subscription(type)
        observer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<List<LydtModel>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<List<LydtModel>>?) {
                        if (t != null) {
                            callback.onSuccess(t.data)
                        } else {
                            callback.onFailure("决策信息获取失败")
                        }
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 资源管护
     */
    override fun resourceManage(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.resourceManage(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        errInfo(e, callback)
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 资源管护-数据管理
     */
    override fun resourceManageData(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.resourceManageData(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 森林防火
     */
    override fun slfh(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.slfh(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 森林防火 数据管理
     */
    override fun slfhData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.slfhData(auth, type, dqcode, year, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<SlfhModel<SlfhModel.Sjgl>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<SlfhModel<SlfhModel.Sjgl>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }


    /**
     * 行政执法
     */
    override fun xzzf(auth: String, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.xzzf(auth, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 行政执法 数据管理
     */
    override fun xzzfData(auth: String, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.xzzfData(auth, dqcode, year, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<XzzfModel<XzzfModel.Sjgl>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<XzzfModel<XzzfModel.Sjgl>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林地征占
     */
    override fun ldzz(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.ldzz(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 行政执法 数据管理
     */
    override fun ldzzData(auth: String, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.ldzzData(auth, dqcode, year, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<LdzzModel<LdzzModel.Sjgl>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<LdzzModel<LdzzModel.Sjgl>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 营造林
     */
    override fun yzl(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.yzl(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<List<YzlModel>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<List<YzlModel>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 营造林 数据管理
     */
    override fun yzlData(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.yzlData(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<List<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<List<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 有害生物
     */
    override fun yhsw(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.yhsw(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 行政执法 数据管理
     */
    override fun yhswData(auth: String, dqcode: String, year: Int, page: Int, size: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.yhswData(auth, dqcode, year, page, size)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<YhswModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<YhswModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 国有林场
     */
    override fun gylc(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.gylc(auth, dqcode)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 国有林场 数据管理
     */
    override fun gylcData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.gylcData(auth, dqcode, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<GylcModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<GylcModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 森林公园
     */
    override fun slgy(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.slgy(auth, dqcode)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 森林公园 数据管理
     */
    override fun slgyData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.slgyData(auth, dqcode, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<SlgyModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<SlgyModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 湿地保护
     */
    override fun sdbh(auth: String, dqcode: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.sdbh(auth, dqcode)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 森林公园 数据管理
     */
    override fun sdbhData(auth: String, dqcode: String, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.sdbhData(auth, dqcode, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<SdbhModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<SdbhModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林业科技
     */
    override fun lykj(auth: String, type: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.lykj(auth, type, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<LykjModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<LykjModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林业产业
     */
    override fun lycy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.lycy(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }


    /**
     * 林业产业 数据管理
     */
    override fun lycyData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.lycyData(auth, type, dqcode, year, page, size)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<LycyModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<LycyModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林权
     */
    override fun lq(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.lq(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林权 数据管理
     */
    override fun lqData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server
                .lqData(auth, type, dqcode, year, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<LQuanModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<LQuanModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 植物检疫
     */
    override fun zwjy(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.zwjy(auth, type, dqcode, year)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 植物检疫 数据管理
     */
    override fun zwjyData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server
                .zwjyData(auth, type, dqcode, year, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<ZwjyModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<ZwjyModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 植物检疫
     */
    override fun cfys(auth: String, type: Int, dqcode: String, year: Int, searchtype: Int,
                      callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.cfys(auth, type, dqcode, year, searchtype)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<Any>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<Any>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 植物检疫 数据管理
     */
    override fun cfysData(auth: String, type: Int, dqcode: String, year: Int, page: Int, size: Int, keyword: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server
                .cfysData(auth, type, dqcode, year, page, size, keyword)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultModel<CfysModel<Any>>> {
                    override fun onError(e: Throwable?) {
                        if (e?.message == null) {
                            callback.onFailure(e.toString())
                        } else {
                            callback.onFailure(e.message!!)
                        }
                    }

                    override fun onNext(t: ResultModel<CfysModel<Any>>?) {
                        callback.onSuccess(t!!)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 网络错误处理
     */
    private fun errInfo(e: Throwable?, callback: RemoteDataSource.mCallback) {
        val err = getErrBody(e)
        when {
            !err.isNullOrEmpty() -> callback.onFailure(err.toString())
            e?.message == null -> callback.onFailure(e.toString())
            else -> callback.onFailure(e.message!!)
        }
    }

    /**
     * 获取网络错误中的异常信息
     */
    fun getErrBody(e: Throwable?): String? {
        var err: String? = ""
        if (e is HttpException) {
            val body = e.response().errorBody()
            try {
//                Log.e("tag", body!!.string())
                val result = body?.string() ?: "{}"
                when (e.code()) {
                    401 -> {
                        val obj = Gson().fromJson(result, ResultModel.Err::class.java)
                        err = obj.message
                    }

                    400 -> {
                        val obj = Gson().fromJson(result, ErrResultModel::class.java)
                        err = obj.responseMsg
                    }
                }
            } catch (IOe: IOException) {
                IOe.printStackTrace()
            }
        }
        return err
    }

    private fun requestBody(json: String): RequestBody {
        var json1 = json
        val gson = Gson()
        json1 = gson.toJson(json1)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json1)
    }
}
