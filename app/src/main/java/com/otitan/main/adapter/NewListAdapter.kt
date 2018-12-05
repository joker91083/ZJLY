package com.otitan.main.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.NewListItemViewModel
import com.otitan.model.LydtModel
import com.otitan.zjly.R

class NewListAdapter() : BaseAdapter() {
    var context: Context? = null
    var items: List<LydtModel>? = null

    constructor(context: Context?, items: List<LydtModel>?) : this() {
        this.context = context
        this.items = items
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_new_list
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = NewListItemViewModel(context)
        viewmodel.model.set(items!![position])
        return viewmodel
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}