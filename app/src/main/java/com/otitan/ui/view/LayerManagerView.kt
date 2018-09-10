package com.otitan.ui.view

import android.util.Log
import android.view.View
import com.esri.arcgisruntime.data.Geodatabase
import com.esri.arcgisruntime.geometry.Geometry
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.layers.Layer
import com.otitan.model.MyLayer
import com.otitan.ui.activity.MapActivity
import com.otitan.ui.adapter.LayerManagerAdapter
import com.otitan.ui.mview.ILayerManager
import com.otitan.ui.mview.ILayerManagerItem
import com.otitan.ui.mview.IMap
import com.otitan.ui.vm.LayerManagerViewModel
import com.otitan.util.ResourcesManager
import com.otitan.util.Utils
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.share_tckz.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileNotFoundException
import kotlin.properties.Delegates

/**
 * 图层控制
 */
class LayerManagerView() : ILayerManager, ILayerManagerItem {
    private var activity: MapActivity by Delegates.notNull()
    private var iMap: IMap by Delegates.notNull()
    var viewModel: LayerManagerViewModel by Delegates.notNull()
    var imgLayer: ArcGISTiledLayer? = null
    var adapter: LayerManagerAdapter? = null
    val checked = HashMap<String, Boolean>()

    constructor(activity: MapActivity, iMap: IMap) : this() {
        this.activity = activity
        this.iMap = iMap
    }

    fun initView() {
        val groups = ResourcesManager.getInstances(activity).getOtmsFolder()
        if (groups.isEmpty()) {
            return
        }
        val childs = ResourcesManager.getInstances(activity).getChildData(groups)
        if (adapter == null) {
            childs.forEach { map ->
                map.forEach { k, list ->
                    list.forEach { file ->
                        checked[file.absolutePath] = false
                    }
                }
            }
            adapter = LayerManagerAdapter(activity, groups, childs, this, checked)
        }
        activity.tckzExplv.setAdapter(adapter)
//        Utils.setExpendHeight(adapter!!, activity.tckzExplv)
    }

    override fun showLayer(type: Int) {
        when (type) {
            1 -> {
                iMap.getTiledLayer()?.isVisible = viewModel.base.get()
            }
            2 -> {
                val list = ResourcesManager.getInstances(activity).getImgTitlePath()
                if (viewModel.img.get()) {
                    if (list.size == 1) {
                        imgLayer = ArcGISTiledLayer(list[0].absolutePath)
                        activity.mv_map.map.basemap.baseLayers.add(imgLayer)
                    } else if (list.size > 1) {
                        activity.icImg.visibility = View.VISIBLE
                        activity.imgManager.setData(list)
                    }
                } else {
                    val layers = activity.mv_map.map.basemap.baseLayers
                    if (list.size == 1) {
                        if (layers.contains(imgLayer)) {
                            layers.remove(imgLayer)
                        }
                    } else if (list.size > 1) {
                        imgLayer = null
                        activity.icImg.visibility = View.GONE
                        val tempList = ArrayList<Layer>()
                        list.forEach { file ->
                            layers.forEach { layer ->
                                if (layer.name == file.name.split(".")[0]) {
                                    tempList.add(layer)
                                }
                            }
                        }
                        layers.removeAll(tempList)
                    }
                }
            }
            3 -> {
            }
        }
    }

    override fun setExtent(type: Int) {
        when (type) {
            1 -> {
                activity.mv_map.setViewpointGeometryAsync(iMap.getTiledLayer()?.fullExtent)
            }
            2 -> {
                if (imgLayer != null) {
                    activity.mv_map.setViewpointGeometryAsync(imgLayer?.fullExtent)
                }
            }
            3 -> {
            }
        }
    }

    override fun addLayer(file: File, checked: Boolean) {
        if (checked) {
            addLayers(file)
        } else {
//            val layers = activity.mv_map.map.basemap.baseLayers
            val layers = iMap.getLayers()
            val temp = ArrayList<MyLayer>()
            layers.forEach {
                if (it.cName == file.name.split(".")[0] && file.parent == it.pName) {
                    activity.mv_map.map.basemap.baseLayers.remove(it.layer)
                    temp.add(it)
                }
            }
            layers.removeAll(temp)
        }
    }

    override fun setExtent(file: File) {
        val geometrys = ArrayList<Geometry?>()
        val layers = iMap.getLayers()
        layers.forEach {
            if (it.cName == file.name.split(".")[0] && file.parent == it.pName) {
                geometrys.add(it.layer?.fullExtent)
            }
        }
        val totalExtent = GeometryEngine.combineExtents(geometrys)
        totalExtent.let {
            activity.mv_map.setViewpointGeometryAsync(it)
        }
    }

    override fun close() {
        activity.icTckz.visibility = View.GONE
    }

    fun addLayers(file: File) {
        try {
            val gdb = Geodatabase(file.absolutePath) ?: return
            gdb.loadAsync()
            gdb.addDoneLoadingListener {
                val list = gdb.geodatabaseFeatureTables
                list.forEach {
                    val layer = FeatureLayer(it)
                    layer.isVisible = true
                    if (iMap.getTiledLayer() != null) {
//                        val sp1 = iMap.getTiledLayer()?.spatialReference
//                        val sp2 = layer.spatialReference
//                        if (sp1 != sp2) {
//                            activity.toast("加载数据与基础底图投影系不同,无法加载")
//                            return@forEach
//                        }
                        activity.mv_map.map.basemap.baseLayers.add(layer)
                        val myLayer = MyLayer()
                        myLayer.pName = file.parent
                        myLayer.cName = file.name.split(".")[0]
                        myLayer.lName = layer.name
                        myLayer.layer = layer
                        myLayer.path = file.absolutePath
                        myLayer.table = it
                        iMap.getLayers().add(myLayer)
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.e("tag", "数据库错误:$e")
        } catch (e: RuntimeException) {
            e.printStackTrace()
            Log.e("tag", "数据库错误:$e")
            activity.toast("数据库错误")
        }
    }
}