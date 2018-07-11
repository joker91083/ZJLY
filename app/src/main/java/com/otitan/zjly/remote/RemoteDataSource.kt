package com.titan.testdemo

/**
 * Created by whs on 2017/6/7
 */

interface RemoteDataSource {

    interface mCallback {

        fun onFailure(info: String)

        fun onSuccess(result: Any)
    }
}
