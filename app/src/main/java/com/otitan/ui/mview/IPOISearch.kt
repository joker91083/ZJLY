package com.otitan.ui.mview

import com.esri.arcgisruntime.geometry.Point

interface IPOISearch {

    fun refresh()

    fun setLocation(point: Point)

    fun close()
}