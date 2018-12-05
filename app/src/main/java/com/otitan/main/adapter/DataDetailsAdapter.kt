package com.otitan.main.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.DataDetailsItemViewModel
import com.otitan.model.DataItemModel
import com.otitan.zjly.R

class DataDetailsAdapter() : BaseAdapter() {

    private var context: Context? = null
    private var items: List<DataItemModel>? = null

    constructor(context: Context?, items: List<DataItemModel>) : this() {
        this.context = context
        this.items = items
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_data_details
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = DataDetailsItemViewModel(context)
        viewmodel.info.set(items!![position])
        return viewmodel
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}