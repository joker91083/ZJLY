package com.otitan.ui.mview

import android.view.View
import com.esri.arcgisruntime.layers.ArcGISTiledLayer

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

    fun getLayerManagerView(): View
}