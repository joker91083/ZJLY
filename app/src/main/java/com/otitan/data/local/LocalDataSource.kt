/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.otitan.data.local

import java.util.*

/**
 * Main entry point for accessing tasks data.
 */
interface LocalDataSource{
    interface Callback {

        fun onFailure(info: String?)

        fun onSuccess(result: Any?)
    }
    /**
     * 保存轨迹点到本地
     * [state] 是否上传服务器的状态值 1已上传 0未上传
     */
    fun addLocalPoint(lon:String,lat:String,sbh:String,state:String)

    /**
     * 轨迹查询
     */
    fun queryTrackPoint(stratime: String, endtime: String, callback: Callback)
}