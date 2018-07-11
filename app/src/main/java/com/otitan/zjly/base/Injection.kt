package com.otitan.zjly.base

/**
 * Created by whs on 2017/5/18
 */

import android.content.Context

import com.otitan.zjly.local.LocalDataSourceImpl
import com.otitan.zjly.remote.RemoteDataSourceImpl


/**
 * Enables injection of mock implementations for
 * [com.titan.data.source.DataRepository] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
object Injection {

    fun provideDataRepository(context: Context): DataRepository {
        //checkNotNull(context);
        return DataRepository.getInstance(LocalDataSourceImpl.getInstance(context), RemoteDataSourceImpl.getInstance(context))
    }
}
