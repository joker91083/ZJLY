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
}