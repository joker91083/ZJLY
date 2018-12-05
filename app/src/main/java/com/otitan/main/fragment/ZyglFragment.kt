package com.otitan.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.otitan.base.BaseFragment
import com.otitan.main.model.TrackPoint
import com.otitan.main.viewmodel.TrackManagerViewModel
import com.otitan.ui.vm.MapToolViewModel
import com.otitan.ui.vm.MapViewModel
import com.otitan.util.Constant
import com.otitan.util.ResourcesManager
import com.otitan.util.SpatialUtil
import com.otitan.util.SymbolUtil
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmMapBinding
import org.jetbrains.anko.toast
import kotlin.properties.Delegates


/**
 *
 */
class ZyglFragment : BaseFragment<FmMapBinding, MapViewModel>(), TrackManagerFragment.TrackManagerDialogListener {

    var zyglViewModel: MapViewModel? = null

    var tiledlayer: ArcGISTiledLayer? = null
    //轨迹查询对话框
    private var mTrackManager: TrackManagerFragment by Delegates.notNull()
    var spatialReferences: SpatialReference? = null
    //绘制图层
    var mGraphicsOverlay: GraphicsOverlay? = null
    //轨迹状态 0查询轨迹
    var guijiState = -1

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_map
    }

    override fun initVariableId(): Int {
        return BR.zyglViewModel
    }

    override fun initViewModel(): MapViewModel {
        if (zyglViewModel == null) {
            zyglViewModel = MapViewModel(this.context!!)
        }
        return zyglViewModel as MapViewModel
    }

    companion object {

        private var zyglFragment: ZyglFragment? = null
        @JvmStatic

        @Synchronized
        fun getInstance(): ZyglFragment? {
            if (zyglFragment == null) {
                zyglFragment = ZyglFragment()
            }
            return zyglFragment
        }
    }

    override fun initParam() {
        super.initParam()
        arguments?.let {
            guijiState = it.getInt("guiji")
        }
    }

    override fun initData() {
        super.initData()

        initView()

        initMap()

        initArcgisLocation()
    }

    protected fun initView() {
        viewModel.mapToolViewModel = MapToolViewModel(this.context!!)

        binding.icMapTool!!.mapToolViewModel = viewModel.mapToolViewModel

        mTrackManager = TrackManagerFragment.getInstance()
        var viewModel = TrackManagerViewModel(mTrackManager, activity)
        mTrackManager.mViewModel = viewModel
    }

    /*加载基础底图*/
    private fun initMap() {
        var instance = ResourcesManager.getInstances(this.context!!)
        var titlepath = instance.getTitlePath()
        if (titlepath.equals("") || titlepath.equals(instance.filePath)) {
            //添加在线地图
            var online = getString(R.string.World_Imagery)
            tiledlayer = ArcGISTiledLayer(online)
        } else {
            //添加离线地图
            tiledlayer = ArcGISTiledLayer(titlepath)
        }

        var gisMap = ArcGISMap()
        binding.mvMap.map = gisMap

        binding.mvMap.map.basemap = Basemap(tiledlayer)

        // 去除下方 powered by esri 按钮
        binding.mvMap.isAttributionTextVisible = false
        gisMap.addLoadStatusChangedListener {
            val mapLoadStatus: String = it.newLoadStatus.name
            // map load status can be any of LOADING, FAILED_TO_LOAD, NOT_LOADED or LOADED
            // set the status in the TextView accordingly
            when (mapLoadStatus) {
                "LOADING" -> Log.e("tag", "LOADING")
                "FAILED_TO_LOAD" -> {
                    activity?.toast("图层加载异常")
//                        viewModel!!.snackbarText.set("图层加载异常")
                    Log.e("tag", "图层加载异常")
                }
                "NOT_LOADED" -> Log.e("tag", "NOT_LOADED")
                "LOADED" -> {
                    Log.e("tag", "图层加载完成")
                    Log.e("tag", "WKID" + binding.mvMap.spatialReference?.wkid)
//                    SpatialUtil.defaultSpatialReference = binding.mvMap.spatialReference
//                    spatialReferences = SpatialUtil.defaultSpatialReference
                }
                else -> {
                }
            }
        }
        mGraphicsOverlay = GraphicsOverlay()
        binding.mvMap.graphicsOverlays?.add(mGraphicsOverlay)
        if (guijiState == 0) {
            mTrackManager.show(fragmentManager, "trackmanager")
        }
    }

    /*初始化定位*/
    fun initArcgisLocation() {
        var display = binding.mvMap.locationDisplay

        display.addLocationChangedListener(LocationDisplay.LocationChangedListener {

            var point = display.location.position
            if (point != null) {
                Log.e("================", point.x.toString())
                Log.e("================", point.y.toString())

//                binding.mvMap.setViewpointCenterAsync(point, Constant.defalutScale)
            }

        })
        display.startAsync()
    }

    /**
     * 绘制轨迹
     */
    override fun drawTrackLine(list: List<TrackPoint>) {
        val points = PointCollection(spatialReferences)
        list.forEach {
            val point: Point = if (spatialReferences != null && spatialReferences!!.wkid == 4326) {
                GeometryEngine.project(Point(it.lon.toDouble(), it.lat.toDouble(), SpatialUtil.spatialWgs4326), spatialReferences) as Point
            } else {
                Point(it.lon.toDouble(), it.lat.toDouble())
            }
            if (!point.isEmpty) {
                points.add(point)
            }
        }
        if (points.size > 0) {
            val polyline = PolylineBuilder(points)
            if (polyline.isSketchValid) {
                val graphic = Graphic(polyline.toGeometry(), SymbolUtil.measureline)
                mGraphicsOverlay?.graphics?.add(graphic)
            }
        } else {
            activity?.toast("未查询到轨迹点")
        }

    }

    /**
     * 关闭轨迹弹窗
     */
    override fun dismiss() {
        mTrackManager.dismiss()
    }
}
