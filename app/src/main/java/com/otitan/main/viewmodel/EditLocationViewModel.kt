package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.util.Log
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.geometry.Point
import com.otitan.base.BaseViewModel
import com.otitan.model.EditLocation
import com.otitan.ui.mview.IEditLocation
import com.otitan.util.ConverterUtils
import com.otitan.util.SpatialUtil
import org.jetbrains.anko.toast

class EditLocationViewModel() : BaseViewModel() {

    var mView: IEditLocation? = null

    //定位类型 1经纬度 2度分秒 3米制
    var locType = ObservableInt(1)
    //坐标数据
    var loc = ObservableField<EditLocation>(EditLocation())

    constructor(context: Context?, mView: IEditLocation) : this() {
        mContext = context
        this.mView = mView
    }

    fun setLocType(type: Int) {
        locType.set(type)
        Log.e("tag", "type:${locType.get()}")
    }

    fun sure() {
        if (!checkValue()) {
            mContext?.toast("请填写完整")
            return
        }
        val point = conversion()
        if (point != null) {
            mView?.sure(point)
        }
    }

    private fun conversion(): Point? {
        var x = 0.0
        var y = 0.0
        when (locType.get()) {
            1 -> {
                x = loc.get()?.lon?.toDouble() ?: 0.0
                y = loc.get()?.lat?.toDouble() ?: 0.0
                // 判断经纬度是否在中国境内
                if (x > 135 || x < 74 || y > 54 || y < 3) {
                    mContext?.toast("坐标范围不在中国内,请重新输入")
                    return null
                }
                return Point(x, y, SpatialUtil.spatialWgs4326)
            }
            2 -> {
                val lonD = ConverterUtils.toDouble(loc.get()?.lonD)
                val lonF = ConverterUtils.toDouble(loc.get()?.lonF)
                val lonM = ConverterUtils.toDouble(loc.get()?.lonM)
                val latD = ConverterUtils.toDouble(loc.get()?.latD)
                val latF = ConverterUtils.toDouble(loc.get()?.latF)
                val latM = ConverterUtils.toDouble(loc.get()?.latM)
                val f = lonF / 60.0
                val m = lonM / 3600.0
                x = lonD + f + m
                y = latD + latF / 60.0 + latM / 3600.0
            }
            3 -> {
                x = loc.get()?.x?.toDouble() ?: 0.0
                y = loc.get()?.y?.toDouble() ?: 0.0
                var p = Point(x, y, SpatialUtil.spatialWgs3857)
                if (!GeometryEngine.isSimple(p)) {
                    p = GeometryEngine.simplify(p) as Point
                }
                val point = GeometryEngine.project(p, SpatialUtil.spatialWgs4326)
                return point as Point
            }
        }
        return Point(x, y, SpatialUtil.spatialWgs4326)
    }

    fun cancel() {
        mView?.cancel()
    }


    fun checkValue(): Boolean {
        var flag = true
        when (locType.get()) {
            1 -> when {
                loc.get()?.lon.isNullOrBlank() -> flag = false
                loc.get()?.lat.isNullOrBlank() -> flag = false
            }
            2 -> when {
                loc.get()?.lonD.isNullOrBlank() -> flag = false
                loc.get()?.lonF.isNullOrBlank() -> flag = false
                loc.get()?.lonM.isNullOrBlank() -> flag = false
                loc.get()?.latD.isNullOrBlank() -> flag = false
                loc.get()?.latF.isNullOrBlank() -> flag = false
                loc.get()?.latM.isNullOrBlank() -> flag = false
            }
            3 -> when {
                loc.get()?.x.isNullOrBlank() -> flag = false
                loc.get()?.y.isNullOrBlank() -> flag = false
            }
        }
        return flag
    }
}