package com.otitan.ui.view

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.mapping.view.Graphic
import com.otitan.main.adapter.POIAdapter
import com.otitan.main.view.MapCenterActivity
import com.otitan.main.viewmodel.POISearchViewModel
import com.otitan.ui.mview.IPOISearch
import com.otitan.util.Constant
import com.otitan.util.SymbolUtil
import com.otitan.zjly.databinding.IncludePoiSearchBinding
import kotlinx.android.synthetic.main.activity_map_center.*
import kotlin.properties.Delegates
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class POISearchView() : IPOISearch {
    var context: Activity? = null
    var view: View? = null
    var adapter: POIAdapter? = null
    var viewmodel: POISearchViewModel by Delegates.notNull()
    var binding: IncludePoiSearchBinding by Delegates.notNull()

    constructor(context: Activity, view: View) : this() {
        this.context = context
        this.view = view
        binding = IncludePoiSearchBinding.bind(view)
//        initView()
    }

    fun initView() {
        if (adapter == null) {
            adapter = POIAdapter(context, viewmodel.items, this)
        }

        binding.rvPoi.adapter = adapter

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewmodel.name = p0.toString()
                if (p0.isNullOrBlank()) {
                    viewmodel.items.clear()
                    (context as MapCenterActivity).showGraphicsOverlay.graphics.clear()
                    refresh()
                }
            }
        })
    }

    override fun setLocation(point: Point) {
        viewmodel.isVisible.set(false)
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow((context as MapCenterActivity).currentFocus.windowToken, 0)
        val graphic = Graphic(point, SymbolUtil.getLocSymbol(context))
        val activity = context as MapCenterActivity
        activity.showGraphicsOverlay.graphics.clear()
        activity.showGraphicsOverlay.graphics.add(graphic)
        activity.mapview.setViewpointGeometryAsync(point, Constant.defalutScale)
    }

    override fun refresh() {
        adapter?.setData(viewmodel.items)
    }

    override fun close() {
        if (binding.edSearch.text.isNullOrBlank()) {
            context?.tv_search?.text = ""
            (context as MapCenterActivity).showGraphicsOverlay.graphics.clear()
        }
    }
}