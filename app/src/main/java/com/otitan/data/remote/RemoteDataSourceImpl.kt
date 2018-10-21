package com.otitan.data.remote

import android.util.Log
import com.google.gson.Gson
import com.otitan.base.ValueCallBack
import com.otitan.model.*
import okhttp3.MediaType
import okhttp3.RequestBody
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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

    override fun addPointToServer(lon: String, lat: String, sbh: String, callback: ValueCallBack<Any>) {
        var observable = RetrofitHelper.instance.server.addPointToServer(lon, lat, sbh)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(s: String) {
                        callback.onSuccess(s)
                    }
                })

    }

    override fun login(username: String, password: String, type: String, callback: RemoteDataSource.mCallback) {
        val loginInfo = LoginInfo()
        loginInfo.password = password
        loginInfo.username = username
        val gson = Gson()
        val body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), gson.toJson(loginInfo))
        val observable = RetrofitHelper.instance.server.login(username, password)
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
                            t.data.access_token != null -> callback.onSuccess(t)
                            else -> callback.onFailure("登录失败")
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
     * 营造林
     */
    override fun yzl(auth: String, type: Int, dqcode: String, year: Int, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.yzl(auth, type, dqcode, year)
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
}
