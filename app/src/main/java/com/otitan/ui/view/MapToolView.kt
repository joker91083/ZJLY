package com.otitan.ui.view

import com.otitan.ui.activity.MapActivity
import com.otitan.ui.mview.IMap
import com.otitan.ui.mview.IMapTool
import kotlinx.android.synthetic.main.activity_map.*

/**
 * 地图工具
 */
class MapToolView() : IMapTool {
    private var activity: MapActivity? = null
    private var iMap: IMap? = null

    constructor(activity: MapActivity, iMap: IMap) : this() {
        this.activity = activity
        this.iMap = iMap
    }

    override fun myLocation() {
        iMap?.myLocation()
    }

}