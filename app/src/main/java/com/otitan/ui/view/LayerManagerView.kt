package com.otitan.ui.view

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.esri.arcgisruntime.data.FeatureTable
import com.esri.arcgisruntime.data.Geodatabase
import com.esri.arcgisruntime.data.ShapefileFeatureTable
import com.esri.arcgisruntime.geometry.Geometry
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.layers.Layer
import com.esri.arcgisruntime.loadable.LoadStatus
import com.otitan.base.BaseAdapter
import com.otitan.main.view.MapCenterActivity
import com.otitan.model.BaseLayer
import com.otitan.model.MyLayer
import com.otitan.ui.adapter.BaseLayerAdapter
import com.otitan.ui.adapter.LayerManagerAdapter
import com.otitan.ui.mview.ILayerManager
import com.otitan.ui.mview.ILayerManagerItem
import com.otitan.ui.mview.IMap
import com.otitan.ui.vm.LayerManagerViewModel
import com.otitan.util.ResourcesManager
import com.otitan.util.TitanItemDecoration
import com.otitan.util.Utils
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_map_center.*
import kotlinx.android.synthetic.main.share_tckz.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileNotFoundException
import kotlin.properties.Delegates

/**
 * 图层控制
 */
class LayerManagerView() : ILayerManager, ILayerManagerItem {
    private var activity: MapCenterActivity by Delegates.notNull()
    private var iMap: IMap by Delegates.notNull()
    var viewModel: LayerManagerViewModel by Delegates.notNull()
    var imgLayer: ArcGISTiledLayer? = null
    var adapter: LayerManagerAdapter? = null
    var baseAdapter: BaseLayerAdapter? = null
    val checked = HashMap<String, Boolean>()
    val baseCheck = HashMap<Int, Boolean>()

    constructor(activity: MapCenterActivity, iMap: IMap) : this() {
        this.activity = activity
        this.iMap = iMap
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun initView() {
        if (baseAdapter == null) {
            val list = ArrayList<BaseLayer>()
            val array = arrayOf("基础图", "影像图", "地形图")
            for (i in 1..2) {
                val baseLayer = BaseLayer()
                baseLayer.name = array[i-1]
                baseLayer.type = i
                list.add(baseLayer)
                baseCheck[i] = i == 1
            }
            baseAdapter = BaseLayerAdapter(activity, list, this, baseCheck)
        }
        val linearLayoutManager = LinearLayoutManager(activity,OrientationHelper.VERTICAL,false)
        activity.tckzRv.layoutManager = linearLayoutManager
        activity.tckzRv.addItemDecoration(TitanItemDecoration(activity,LinearLayout.VISIBLE,0))
        activity.tckzRv.adapter = baseAdapter

        val groups = ResourcesManager.getInstances(activity).getOtmsFolder()
        if (groups.isEmpty()) {
            return
        }
        val childs = ResourcesManager.getInstances(activity).getChildData(groups)
        if (adapter == null) {
//            childs.forEach { map ->
//                map.forEach { k, list ->
//                    list.forEach { file ->
//                        checked[file.absolutePath] = false
//                    }
//                }
//            }

            for(item in childs){
                for(map in item){
                    for(file in map.value){
                        checked[file.absolutePath] = false
                    }
                }
            }
            adapter = LayerManagerAdapter(activity, groups, childs, this, checked)
        }
        activity.tckzExplv.setAdapter(adapter)
//        Utils.setExpendHeight(adapter!!, activity.tckzExplv)
    }

    override fun showLayer(type: Int,check:Boolean) {
        when (type) {
            1 -> {
                iMap.getOpenStreetLayer()?.isVisible = check
            }
            2 -> {
                val list = ResourcesManager.getInstances(activity).getImgTitlePath()
                if (check) {
                    if (list.size == 1) {
                        imgLayer = ArcGISTiledLayer(list[0].absolutePath)
                        activity.mapview.map.operationalLayers.add(imgLayer)
                    } else if (list.size > 1) {
                        activity.include_img.visibility = View.VISIBLE
                        activity.imgManager.setData(list)
                    }
                } else {
                    val layers = activity.mapview.map.operationalLayers
                    if (list.size == 1) {
                        if (layers.contains(imgLayer)) {
                            layers.remove(imgLayer)
                        }
                    } else if (list.size > 1) {
                        imgLayer = null
                        activity.include_img.visibility = View.GONE
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
                activity.mapview.setViewpointGeometryAsync(iMap.getOpenStreetLayer()?.fullExtent)
            }
            2 -> {
                if (imgLayer != null) {
                    activity.mapview.setViewpointGeometryAsync(imgLayer?.fullExtent)
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
                val pPath = file.parent.split("/")
                if (it.getcName() == file.name.split(".")[0] && pPath[pPath.size - 1] == it.getpName()) {
                    activity.mapview.map.operationalLayers.remove(it.layer)
                    temp.add(it)
                }
            }
            layers.removeAll(temp)
        }
    }

    override fun setExtent(file: File) {
        val geometrys = ArrayList<Geometry>()
        val layers = iMap.getLayers()
        layers.forEach { it ->
            val pPath = file.parent.split("/")
            if (it.getcName() == file.name.split(".")[0] && pPath[pPath.size - 1] == it.getpName()) {
                val g = it.layer?.fullExtent
                g?.let {
                    geometrys.add(g)
                }
            }
        }
        if (geometrys.isNotEmpty()) {
            val totalExtent = GeometryEngine.combineExtents(geometrys)
            totalExtent.let {
                activity.mapview.setViewpointGeometryAsync(it)
            }
        }
    }

    override fun close() {
        activity.include_icTckz.visibility = View.GONE
    }

    fun addLayers(file: File) {

        if (file.exists() && file.name.endsWith("geodatabase")) {
            addGeodatabase(file)
        } else if (file.exists() && file.name.endsWith("shp")) {
            addShp(file)
        }
    }

    fun addGeodatabase(file: File) {

        try {
            val gdb = Geodatabase(file.absolutePath) ?: return
            gdb.loadAsync()
            gdb.addDoneLoadingListener {
                val list = gdb.geodatabaseFeatureTables
                list.forEach {
                    val layer = FeatureLayer(it)
                    layer.isVisible = true
                    if (iMap.getOpenStreetLayer() != null) {
//                        val sp1 = iMap.getOpenStreetLayer()?.spatialReference!!.wkid
//                        val sp2 = layer.spatialReference?.wkid
//                        if (sp1 != sp2) {
//                            activity.toast("加载数据与基础底图投影系不同,无法加载")
//                            return@forEach
//                        }
                        activity.mapview.map.operationalLayers.add(layer)
                        val myLayer = MyLayer()
                        val array = file.parent.split("/")
                        myLayer.setpName(array[array.size - 1])
                        myLayer.setcName(file.name.split(".")[0])
                        myLayer.setlName(layer.name)
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

    fun addShp(file: File) {
        var path = file.path
        var table = ShapefileFeatureTable(path)
        table.loadAsync()
        table.addDoneLoadingListener {
            var statuts = table.loadStatus
            if (statuts == LoadStatus.LOADING) {
                var featureLayer: FeatureLayer = FeatureLayer(table)
                activity.mapview.map.operationalLayers.add(featureLayer)
            } else {
                val error = table.getLoadError().message
                val tip = "Shapefile feature table failed to load: " + table.getLoadError().toString()
                Log.e("加载shp数据", error)
            }
        }
    }
}