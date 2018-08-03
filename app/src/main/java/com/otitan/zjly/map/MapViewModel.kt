package com.otitan.zjly.map

import com.esri.arcgisruntime.geometry.Point
import com.otitan.zjly.base.BaseViewModel
import com.otitan.zjly.base.DataRepository

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
        mView?.showToast(0, "图层信息")
    }

    /**
     * 测距
     */
    fun getDistance() {
        mView?.showToast(0, "测距")
    }

    /**
     * 勾绘
     */
    fun onSketch() {
        mView?.showToast(0, "勾绘")
    }

    /**
     * 清除编辑
     */
    fun onClean() {
        mView?.showToast(0, "清除编辑")
    }

    /**
     * 当前位置
     */
    fun getLocation() {
        mView?.showToast(0, "当前位置")
        mView?.myLocation()
    }

    /**
     * 图层控制
     */
    fun layerControl() {
        mView?.showToast(0, "图层控制")
    }

    /**
     * GPS设置
     */
    fun systemSetting() {
        mView?.showToast(0, "GPS设置")
    }

    /**
     * 数据采集
     */
    fun dataCollection() {
        mView?.showToast(0, "数据采集")
    }

    /**
     * 小班编辑
     */
    fun classEditor() {
        mView?.showToast(0, "小班编辑")
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