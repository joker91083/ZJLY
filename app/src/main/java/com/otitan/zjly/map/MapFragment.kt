package com.otitan.zjly.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
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
}