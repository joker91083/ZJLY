package com.otitan.ui.adapter

import android.content.Context
import com.otitan.base.BaseAdapter
import com.otitan.model.BaseLayer
import com.otitan.ui.mview.ILayerManager
import com.otitan.ui.vm.LayerManagerViewModel
import com.otitan.zjly.R
import kotlin.properties.Delegates

class BaseLayerAdapter() : BaseAdapter() {
    private var context: Context by Delegates.notNull()
    private var items: List<BaseLayer> by Delegates.notNull()
    private var mView: ILayerManager by Delegates.notNull()
    private var checkedMap: HashMap<Int, Boolean>? = null

    constructor(context: Context, items: List<BaseLayer>, mView: ILayerManager,
                checked: HashMap<Int, Boolean>) : this() {
        this.context = context
        this.items = items
        this.mView = mView
        this.checkedMap = checked
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_baselayer
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = LayerManagerViewModel(context, mView)
        viewmodel.name.set(items[position].name)
        viewmodel.type = items[position].type
        viewmodel.check.set(checkedMap!![items[position].type]!!)
        viewmodel.checkedMap = checkedMap
        return viewmodel
    }

    override fun getItemCount(): Int {
        return items.size
    }
}