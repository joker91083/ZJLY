package com.otitan.ui.activity

import android.Manifest
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.util.Log
import android.view.View
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.otitan.base.BaseActivity
import com.otitan.model.Test
import com.otitan.permissions.PermissionsActivity
import com.otitan.permissions.PermissionsChecker
import com.otitan.ui.mview.IMap
import com.otitan.ui.view.LayerManagerView
import com.otitan.ui.vm.LayerManagerViewModel
import com.otitan.ui.vm.MapViewModel
import com.otitan.util.ResourcesManager
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.ActivityMapBinding
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.toast
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(), IMap {
    private var viewmodel: MapViewModel? = null
    private var layerManager: LayerManagerView by Delegates.notNull()
    var tiledlayer: ArcGISTiledLayer? = null

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_map
    }

    override fun initViewModel(): MapViewModel {
        if (viewmodel == null) {
            viewmodel = MapViewModel(this, this)
        }
        return viewmodel as MapViewModel
    }

    /** 动态获取权限 */
    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

    var currentPoint: Point? = null
    //是否是第一次定位 true是 false否
    var isFirst = true

    override fun onStart() {
        super.onStart()
        if (PermissionsChecker(this).lacksPermissions(*permissions)) {
            PermissionsActivity.startActivityForResult(this, PermissionsActivity.PERMISSIONS_REQUEST_CODE, *permissions)
        }
    }

    override fun initData() {
        initView()
        initMap()
        initLocation()
    }

    /**
     * 初始化页面
     */
    fun initView() {
        layerManager = LayerManagerView(this, this)
        viewModel.layerManagerViewModel = LayerManagerViewModel(this, layerManager)
        layerManager.viewModel = viewModel.layerManagerViewModel
    }

    /**
     * 初始化地图
     */
    private fun initMap() {
        try {
            val path = ResourcesManager.getInstances(this).getTitlePath()
            tiledlayer = if (File(path).exists()) {
                ArcGISTiledLayer(path)
            } else {
                ArcGISTiledLayer(getString(R.string.World_Imagery))
            }
            val arcGISMap = ArcGISMap(Basemap(tiledlayer))
            binding.mvMap.map = arcGISMap
            // 去除下方 powered by esri 按钮
            binding.mvMap.isAttributionTextVisible = false

        } catch (e: Exception) {
            Log.e("tag", "地图初始化异常:$e")
            toast("地图初始化异常:$e")
        }
    }

    /**
     * 百度定位
     */
    fun bdLocation() {
        val mLocClient: LocationClient? = LocationClient(this)
        val mCliOption = LocationClientOption()
        mCliOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy // 设置定位模式
        mCliOption.isOpenGps = true // 打开GPS
        mCliOption.setCoorType("bd09ll") // 设置坐标系 bd09ll gcj02
//        mCliOption.setScanSpan(5000) // 定位回掉时间(毫秒)
        mLocClient?.locOption = mCliOption

        //注册位置监听器
        mLocClient?.registerLocationListener(object : BDLocationListener {
            override fun onReceiveLocation(bdLocation: BDLocation?) {

                if (bdLocation != null) {
                    val lat = bdLocation.latitude
                    val lon = bdLocation.longitude

                    currentPoint = Point(bdLocation.longitude, bdLocation.latitude)
                }
            }

            fun onConnectHotSpotMessage(s: String, i: Int) {

            }
        })
        mLocClient?.start()
    }

    /**
     * 初始化地图
     */
    fun initLocation() {
        // 当前位置
        val mLocDisplay = binding.mvMap.locationDisplay
        // 当位置符号移动到范围之外时，通过重新定位位置符号来保持屏幕上的位置符号
        mLocDisplay?.autoPanMode = LocationDisplay.AutoPanMode.OFF
        // 设置显示的位置
        mLocDisplay?.navigationPointHeightFactor = 0.5.toFloat()
        // 定位显示
        mLocDisplay?.let {
            if (!it.isStarted) {
                it.startAsync()
            }
        }
        mLocDisplay?.addDataSourceStatusChangedListener { p0 ->
            if (!p0.isStarted || p0.error != null) {
                val message = String.format("Error in DataSourceStatusChangedListener:",
                        p0.source.locationDataSource.error.message)
                Log.e("tag", "error:$message")
            }
        }
        // 设置位置变化监听
        mLocDisplay?.addLocationChangedListener { p0 ->
            currentPoint = p0?.location?.position
            if (isFirst && currentPoint != null) {
                myLocation()
                isFirst = false
            }
            Log.e("tag", "flag:${mLocDisplay.isStarted},${p0.location},${p0.location.position}")
        }
    }

    /**
     * 当前位置
     */
    override fun myLocation() {
        currentPoint?.let {
            binding.mvMap.setViewpointCenterAsync(it, 10000.0)
            return
        }
    }

    override fun showTckz() {
        icTckz.visibility = View.VISIBLE
    }

    override fun getTiledLayer(): ArcGISTiledLayer? {
        return tiledlayer
    }

    override fun getLayerManagerView(): View {
        return icTckz
    }

    override fun onResume() {
        super.onResume()
        binding.mvMap.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.mvMap.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mvMap.dispose()
    }
}