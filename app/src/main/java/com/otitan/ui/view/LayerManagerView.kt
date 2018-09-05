package com.otitan.ui.view

import android.app.Activity
import android.content.Context
import android.view.View
import com.otitan.ui.mview.ILayerManager
import com.otitan.ui.mview.IMap
import com.otitan.ui.vm.LayerManagerViewModel
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.share_tckz.*

class LayerManagerView() : ILayerManager {
    private var mContext: Context by Delegates.notNull()
    private var iMap: IMap by Delegates.notNull()
    var viewModel: LayerManagerViewModel by Delegates.notNull()

    constructor(context: Context, iMap: IMap) : this() {
        this.mContext = context
        this.iMap = iMap
    }

    fun initView() {

    }

    override fun showLayer(type: Int) {
        when (type) {
            1 -> {
                iMap.getTiledLayer()?.isVisible = viewModel.base.get()
            }
            2->{}
            3->{}
        }
    }

    override fun setExtent() {

    }

    override fun close() {
        (mContext as Activity).icTckz.visibility = View.GONE
    }
}