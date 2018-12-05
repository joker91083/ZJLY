package com.otitan.ui.mview

import com.esri.arcgisruntime.geometry.Point

interface IEditLocation {
    fun sure(point: Point)
    fun cancel()
}