package com.otitan.zjly.map

import com.esri.arcgisruntime.geometry.Point
import com.otitan.zjly.base.BaseViewModel
import com.otitan.zjly.base.DataRepository
import org.jetbrains.anko.toast

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
class MapViewModel() : BaseViewModel() {

    private var mView: MapModel ?= null

    var currentPoint : Point? = Point(0.0, 0.0)

    constructor(mapModel: MapModel?, dataRepository: DataRepository) : this() {
        this.mView = mapModel
        this.mDataRepository = dataRepository
    }

    /**
     * 图层信息
     */
    fun showInfo() {
        mView?.getBaseContext()?.toast("图层信息")
    }

    /**
     * 测距
     */
    fun getDistance() {
        mView?.getBaseContext()?.toast("测距")
    }

    /**
     * 勾绘
     */
    fun onSketch() {
        mView?.getBaseContext()?.toast("勾绘")
    }

    /**
     * 清除编辑
     */
    fun onClean() {
        mView?.getBaseContext()?.toast("清除编辑")
    }

    /**
     * 当前位置
     */
    fun getLocation() {
        mView?.getBaseContext()?.toast("当前位置")
        mView?.myLocation()
    }

    /**
     * 图层控制
     */
    fun layerControl() {
        mView?.getBaseContext()?.toast("图层控制")
    }

    /**
     * GPS设置
     */
    fun systemSetting() {
        mView?.getBaseContext()?.toast("GPS设置")
    }

    /**
     * 数据采集
     */
    fun dataCollection() {
        mView?.getBaseContext()?.toast("数据采集")
    }

    /**
     * 小班编辑
     */
    fun classEditor() {
        mView?.getBaseContext()?.toast("小班编辑")
    }

    /*inner class MyBDLocation: BDAbstractLocationListener(){
        override fun onReceiveLocation(p0: BDLocation?) {
            if (p0 != null) {
                val lat = p0.latitude
                val lon = p0.longitude

                currentPoint = Point(p0.longitude, p0.latitude)
            }
        }
    }*/
}