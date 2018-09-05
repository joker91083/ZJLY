package com.otitan.ui.mview

interface ILayerManager{
    /**
     * 显示图层 [type] 图层类型 1基础图 2影像图 3地形图
     */
    fun showLayer(type: Int)

    fun setExtent()
    /**
     * 关闭窗口
     */
    fun close()
}