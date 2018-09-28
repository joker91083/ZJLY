package com.otitan.ui.mview

import com.esri.arcgisruntime.geometry.SpatialReference
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.OpenStreetMapLayer
import com.otitan.model.MyLayer

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
interface IMap {

    /**
     * 图层控制
     */
    fun showTckz()
    //小班编辑
    fun showXbbj()

    fun getTiledLayer(): ArcGISTiledLayer?

    fun getOpenStreetLayer():OpenStreetMapLayer?

    fun getLayers(): ArrayList<MyLayer>

    fun setSpatial(spatialReference: SpatialReference)
}