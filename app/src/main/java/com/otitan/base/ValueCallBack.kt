package com.otitan.base

import com.esri.arcgisruntime.geometry.Geometry


/**
 * Created by otitan_li on 2018/7/20.
 * ValueCallBack
 */

public interface ValueCallBack<T> {

    fun onSuccess(t: T)

    fun onFail(code: String)

    fun onGeometry(geometry: Geometry)
}
