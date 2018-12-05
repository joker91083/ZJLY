package com.otitan.main.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.POISearchItemViewModel
import com.otitan.model.POIModel
import com.otitan.ui.mview.IPOISearch
import com.otitan.zjly.R

class POIAdapter() : BaseAdapter() {

    var context: Context? = null
    var items: List<POIModel>? = null
    var mView: IPOISearch? = null

    constructor(context: Context?, items: ArrayList<POIModel>, mView: IPOISearch) : this() {
        this.context = context
        this.items = items
        this.mView = mView
    }

    fun setData(items: List<POIModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_poi
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = POISearchItemViewModel(context,mView)
        viewmodel.item.set(items!![position])
        return viewmodel
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}