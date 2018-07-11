package com.otitan.zjly.remote

import android.content.Context
import com.titan.testdemo.RemoteDataSource

/**
 * Created by whs on 2017/5/18
 * 后段数据接口实现
 */

class RemoteDataSourceImpl(private val mContext: Context) : RemoteDataSource {


    companion object {
        private var remoteDataSourceImpl: RemoteDataSourceImpl? = null

        fun getInstance(context: Context): RemoteDataSourceImpl {
            if (remoteDataSourceImpl == null) {
                remoteDataSourceImpl = RemoteDataSourceImpl(context)
            }
            return remoteDataSourceImpl as RemoteDataSourceImpl
        }
    }
}
