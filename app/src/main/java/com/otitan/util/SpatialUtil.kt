package com.otitan.util


import android.util.Log
import com.esri.arcgisruntime.geometry.SpatialReference

/**
 * Created by otitan_li on 2018/5/10.
 * SpatialUtil 投影转换工具
 */

class SpatialUtil {
    companion object {

        private var spatialReference: SpatialReference? = null

        var defaultSpatialReference: SpatialReference?
            get() = if (spatialReference != null) {
                spatialReference
            } else SpatialReference.create(3857)
            set(s) {
                if (s != null) {
                    val k = s.wkid
                    Log.e("tag","wkid:$k")
                    spatialReference = s
                }
            }

        val spatialWgs4326: SpatialReference
            get() = SpatialReference.create(4326)

        val spatialWgs3857: SpatialReference
            get() = SpatialReference.create(3857)

        val spatialWgs2343: SpatialReference
            get() = SpatialReference.create(2343)
    }


}
