package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.esri.arcgisruntime.geometry.Point
import com.otitan.base.BaseViewModel
import com.otitan.main.view.MapCenterActivity
import com.otitan.model.POIModel
import com.otitan.ui.mview.IPOISearch
import com.otitan.util.SpatialUtil
import kotlinx.android.synthetic.main.activity_map_center.*
import org.jetbrains.anko.toast

class POISearchItemViewModel() : BaseViewModel() {

    val item = ObservableField<POIModel>()

    var mView: IPOISearch? = null

    constructor(context: Context?, mView: IPOISearch?) : this() {
        mContext = context
        this.mView = mView
    }

    fun setLocation() {
        val x = item.get()?.lng?.toDouble() ?: 0.0
        val y = item.get()?.lat?.toDouble() ?: 0.0
        if (x == 0.0 || y == 0.0) {
            mContext?.toast("坐标错误")
            return
        }
        val point = Point(x, y, SpatialUtil.spatialWgs4326)
        (mContext as MapCenterActivity?)?.tv_search?.text = item.get()?.name
        mView?.setLocation(point)
    }
}