package com.otitan.util


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
            } else SpatialReference.create(4490)
            set(s) {
                if (s != null) {
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
