package com.otitan.ui.adapter

import android.app.Activity
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.otitan.base.BaseAdapter
import com.otitan.ui.mview.ILayerManagerItem
import com.otitan.ui.vm.LayerManagerItemViewModel
import com.otitan.zjly.R
import java.io.File

class ImgManagerAdapter(val activity: Activity, val mView: ILayerManagerItem, var items: List<File>) : BaseAdapter() {

    fun setData(list: List<File>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_tckz_child
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = LayerManagerItemViewModel(activity, mView)
        viewmodel.file = items[position]
        viewmodel.title.set(items[position].name.split(".")[0])
        return viewmodel
    }

    override fun getItemCount(): Int {
        return items.size
    }
}