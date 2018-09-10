package com.otitan.ui.mview

import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.otitan.model.MyLayer

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
interface IMap {

    /**
     * 当前位置
     */
    fun myLocation()

    /**
     * 图层控制
     */
    fun showTckz()

    fun getTiledLayer(): ArcGISTiledLayer?

    fun getLayers(): ArrayList<MyLayer>
}