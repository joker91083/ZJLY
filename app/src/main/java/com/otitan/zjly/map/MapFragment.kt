package com.otitan.zjly.map

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.esri.arcgisruntime.data.TileCache
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.otitan.zjly.R
import com.otitan.zjly.base.BaseFragment
import com.otitan.zjly.databinding.FmMapBinding
import com.otitan.zjly.permissions.PermissionsChecker

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
class MapFragment : BaseFragment(), MapModel {

    var binding :  FmMapBinding ?= null
    var viewModel : MapViewModel ?= null
    var dialog : MaterialDialog ?= null

    /** 动态获取权限 */
    val REQUEST_CODE = 10000 // 权限请求码
    var permissionsChecker : PermissionsChecker? = null
    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

    var currentPoint : Point? = null

    companion object {
        fun getInstance() : MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FmMapBinding.inflate(inflater, container,false)
        binding?.mapViewModel = viewModel

        permissionsChecker = PermissionsChecker(activity)

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMap()
        initLocation()
        // 缺少权限时, 进入权限配置页面
        if (permissionsChecker!!.lacksPermissions(*permissions)) {
            this.activity?.let { ActivityCompat.requestPermissions(it, permissions, REQUEST_CODE) }
        }
    }

    /**
     * 初始化地图
     */
    private fun initMap() {
        try {
            val arcGISMap : ArcGISMap ?= ArcGISMap(Basemap.createImagery())
            val tileCache: TileCache? = TileCache(activity?.resources?.getString(R.string.World_Imagery))
            val layer = ArcGISTiledLayer(tileCache)
            arcGISMap?.basemap?.baseLayers?.add(layer)
            binding?.mvMap?.map = arcGISMap

            // 去除下方 powered by esri 按钮
            binding?.mvMap?.isAttributionTextVisible = false

        } catch (e: Exception) {
            Log.e("tag", "地图初始化异常:$e")
            showToast(0, "地图初始化异常:$e")
        }
    }

    /**
     * 百度定位
     */
    fun bdLocation() {
        val mLocClient : LocationClient?= LocationClient(activity)
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
        val mLocDisplay = binding?.mvMap?.locationDisplay
        // 当位置符号移动到范围之外时，通过重新定位位置符号来保持屏幕上的位置符号
        mLocDisplay?.autoPanMode = LocationDisplay.AutoPanMode.COMPASS_NAVIGATION
        // 设置显示的位置
        mLocDisplay?.navigationPointHeightFactor = 0.5.toFloat()
        // 定位显示
        mLocDisplay?.let {
            if (!it.isStarted){
                it.startAsync()
            }
        }
        // 设置位置变化监听
        mLocDisplay?.addLocationChangedListener {
            p0 -> currentPoint = p0?.location?.position
        }
        mLocDisplay?.addLocationChangedListener(object :LocationDisplay.LocationChangedListener{
            override fun onLocationChanged(p0: LocationDisplay.LocationChangedEvent?) {
                currentPoint = p0?.location?.position
            }
        })
    }

    /**
     * 当前位置
     */
    override fun myLocation() {
        currentPoint?.let {
            binding?.mvMap?.setViewpointCenterAsync(it)
            return
        }
        showToast(0,"没有获取到当前位置")
    }
}