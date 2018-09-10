package com.otitan.ui.view

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.View
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.otitan.ui.adapter.ImgManagerAdapter
import com.otitan.ui.mview.IImgManager
import com.otitan.ui.mview.ILayerManagerItem
import com.otitan.ui.mview.IMap
import com.otitan.util.TitanItemDecoration
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.img_tuceng.*
import java.io.File
import kotlin.properties.Delegates

/**
 * 图层控制-影像图列表
 */
class ImgManagerView() : IImgManager, ILayerManagerItem {
    private var activity: Activity by Delegates.notNull()
    private var iMap: IMap by Delegates.notNull()
    var adapter: ImgManagerAdapter? = null

    constructor(activity: Activity, iMap: IMap) : this() {
        this.activity = activity
        this.iMap = iMap
        initView()
    }

    fun initView() {
        val rvImg = activity.rvImg
        rvImg.layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        rvImg.addItemDecoration(TitanItemDecoration(activity, LinearLayoutManager.VERTICAL, 10))
        val list = ArrayList<File>()
        if (adapter == null) {
            adapter = ImgManagerAdapter(activity, this, list)
        }
        rvImg.adapter = adapter
    }

    fun setData(list: List<File>) {
        adapter?.setData(list)
    }

    override fun addLayer(file: File, checked: Boolean) {
        val layer = ArcGISTiledLayer(file.absolutePath)
        val layers = activity.mv_map.map.basemap.baseLayers
        var flag = false
        run breaking@{
            layers.forEach {
                if ((it as ArcGISTiledLayer).uri == file.absolutePath) {
                    flag = true
                    return@breaking
                }
            }
        }
        if (flag && !checked) {
            layers.remove(layer)
        } else {
            layers.add(layer)
        }
    }

    override fun setExtent(file: File) {
        val layers = activity.mv_map.map.basemap.baseLayers
        layers.forEach {
            if ((it as ArcGISTiledLayer).uri == file.absolutePath) {
                val fullExtent = it.fullExtent
                fullExtent.let { extent ->
                    activity.mv_map.setViewpointGeometryAsync(extent)
                }
            }
        }
    }

    override fun close() {
        activity.icImg.visibility = View.GONE
    }

}