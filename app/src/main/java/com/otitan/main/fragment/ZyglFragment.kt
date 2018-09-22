package com.otitan.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.otitan.base.BaseFragment
import com.otitan.ui.vm.MapToolViewModel
import com.otitan.ui.vm.MapViewModel
import com.otitan.util.Constant
import com.otitan.util.ResourcesManager

import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmMapBinding


/**
 *
 */
class ZyglFragment : BaseFragment<FmMapBinding,MapViewModel>() {

    var zyglViewModel: MapViewModel? = null

    var tiledlayer: ArcGISTiledLayer? = null


    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_map
    }

    override fun initVariableId(): Int {
        return com.otitan.zjly.BR.zyglViewModel
    }

    override fun initViewModel(): MapViewModel {
        if(zyglViewModel == null){
            zyglViewModel = MapViewModel(this.context!!)
        }
        return zyglViewModel as MapViewModel
    }

    companion object {

        private var zyglFragment: ZyglFragment?= null
        @JvmStatic

        @Synchronized fun getInstance(): ZyglFragment?{
            if(zyglFragment == null){
                zyglFragment = ZyglFragment()
            }
            return zyglFragment
        }
    }

    override fun initData() {
        super.initData()

        initView()

        initMap()

        initArcgisLocation()
    }

    protected fun initView(){
        viewModel.mapToolViewModel = MapToolViewModel(this.context!!)

        binding.icMapTool!!.mapToolViewModel = viewModel.mapToolViewModel
    }

    /*加载基础底图*/
    private fun initMap(){
        var instance = ResourcesManager.getInstances(this.context!!)
        var titlepath = instance.getTitlePath()
        if(titlepath.equals("") || titlepath.equals(instance.filePath)){
            //添加在线地图
            var online = getString(R.string.World_Imagery)
            tiledlayer = ArcGISTiledLayer(online)
        }else{
            //添加离线地图
            tiledlayer = ArcGISTiledLayer(titlepath)
        }

        var gisMap = ArcGISMap()
        binding.mvMap.map = gisMap

        binding.mvMap.map.basemap = Basemap(tiledlayer)

        // 去除下方 powered by esri 按钮
        binding.mvMap.isAttributionTextVisible = false


    }

    /*初始化定位*/
    fun initArcgisLocation(){
        var display = binding.mvMap.locationDisplay

        display.addLocationChangedListener(LocationDisplay.LocationChangedListener {

            var point = display.location.position
            if(point != null){
                Log.e("================", point.x.toString())
                Log.e("================", point.y.toString())

                binding.mvMap.setViewpointCenterAsync(point,Constant.defalutScale)
            }

        })
        display.startAsync()
    }



}
