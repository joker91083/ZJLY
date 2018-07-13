package com.otitan.zjly.map

import com.otitan.zjly.base.BaseViewModel
import com.otitan.zjly.base.DataRepository

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
class MapViewModel() : BaseViewModel() {

    private var mView: MapModel ?= null

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
}