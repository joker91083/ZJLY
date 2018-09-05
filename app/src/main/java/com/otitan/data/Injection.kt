package com.otitan.data

/**
 * Created by hanyw on 2018/8/18
 */

import com.otitan.data.local.LocalDataSourceImpl
import com.otitan.data.remote.RemoteDataSourceImpl


/**
 * Enables injection of mock implementations for
 * [com.titan.data.DataRepository] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
object Injection {
    fun provideDataRepository() = DataRepository.getInstance(LocalDataSourceImpl.instances, RemoteDataSourceImpl.instance)
}
