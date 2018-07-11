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

package com.otitan.zjly.local

import android.content.Context
import com.titan.data.source.local.LocalDataSource

/**
 * Concrete implementation of a data source as a db.
 */
class LocalDataSourceImpl// Prevent direct instantiation.
private constructor(
        //private DbHelper mDbHelper;

        private val mContext: Context) : LocalDataSource {

    companion object {

        private var sourceImpl: LocalDataSourceImpl? = null

        fun getInstance(context: Context): LocalDataSourceImpl {
            if (sourceImpl == null) {
                sourceImpl = LocalDataSourceImpl(context)
            }
            return sourceImpl as LocalDataSourceImpl
        }
    }

}
