package com.otitan.zjly.map

import android.os.Bundle
import android.util.Log
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.esri.arcgisruntime.data.TileCache
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.otitan.zjly.R
import com.otitan.zjly.base.BaseFragment
import com.otitan.zjly.databinding.FmMapBinding

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
class MapFragment : BaseFragment(), MapModel {

    var binding :  FmMapBinding ?= null
    var viewModel : MapViewModel ?= null
    var dialog : MaterialDialog ?= null

    companion object {
        fun getInstance() : MapFragment {
            return MapFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FmMapBinding.inflate(inflater, container,false)
        binding?.mapViewModel = viewModel
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMap()
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
}