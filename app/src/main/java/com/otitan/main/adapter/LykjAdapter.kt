package com.otitan.main.adapter

import android.content.Context
import com.google.gson.internal.LinkedTreeMap
import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.LykjItemViewModel
import com.otitan.model.LykjModel
import com.otitan.zjly.R
import kotlin.properties.Delegates

class LykjAdapter() : BaseAdapter() {
    private var context: Context? = null
    private var items: List<Any>? = null
    private var isKjbz:Boolean = false

    constructor(context: Context?, items: List<Any>?) : this() {
        this.context = context
        this.items = items
    }

    fun setData(items: List<Any>?, isKjbz: Boolean) {
        this.items = items
        this.isKjbz = isKjbz
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_lykj
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = LykjItemViewModel()
        viewmodel.xmmc.set((items!![position] as LinkedTreeMap<String, Any>)["Name"].toString())
        viewmodel.xmbh.set((items!![position] as LinkedTreeMap<String, Any>)["No"].toString())
        viewmodel.zcr.set((items!![position] as LinkedTreeMap<String, Any>)["HostUser"].toString())
        viewmodel.cddw.set((items!![position] as LinkedTreeMap<String, Any>)["Unit"].toString())
        viewmodel.isKjbz.set(isKjbz)
        return viewmodel
    }

    override fun getItemCount(): Int {
        return if (items == null || items!!.isEmpty()) 0 else items!!.size
    }
}