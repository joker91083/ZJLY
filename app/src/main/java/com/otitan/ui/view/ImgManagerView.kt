package com.otitan.ui.view

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.otitan.ui.adapter.ImgManagerAdapter
import com.otitan.ui.mview.IImgManager
import com.otitan.ui.mview.ILayerManagerItem
import com.otitan.ui.mview.IMap
import com.otitan.util.TitanItemDecoration
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_map_center.*
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
        rvImg.layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false) as RecyclerView.LayoutManager?
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
        var layer = ArcGISTiledLayer(file.absolutePath)
        val layers = activity.mapview.map.basemap.baseLayers
        var flag = false
        for (l in layers){
            if (l is ArcGISTiledLayer) {
                if (l.uri == file.absolutePath) {
                    flag = true
                    layer = l
                    break
                }
            }
        }
//        run breaking@{
//            layers.forEach {
//                if (it is ArcGISTiledLayer) {
//                    if (it.uri == file.absolutePath) {
//                        flag = true
//                        layer = it
//                        return@breaking
//                    }
//                }
//            }
//        }
        if (flag && !checked) {
            activity.mapview.map.basemap.baseLayers.remove(layer)
        } else {
            activity.mapview.map.basemap.baseLayers.add(layer)
        }
    }

    override fun setExtent(file: File) {
        val layers = activity.mapview.map.basemap.baseLayers
        for (layer in layers){
            if (layer is ArcGISTiledLayer) {
                if (layer.uri == file.absolutePath) {
                    val fullExtent = layer.fullExtent
                    fullExtent.let { extent ->
                        activity.mapview.setViewpointGeometryAsync(extent)
                    }
                }
            }
        }
    }

    override fun close() {
        activity.icImg.visibility = View.GONE
    }

}