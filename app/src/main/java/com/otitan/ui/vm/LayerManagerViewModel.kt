package com.otitan.ui.vm

import android.content.Context
import android.databinding.ObservableBoolean
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.ILayerManager
import kotlin.properties.Delegates

class LayerManagerViewModel() : BaseViewModel() {
    private var mView: ILayerManager by Delegates.notNull()
    val base = ObservableBoolean(true)
    val img = ObservableBoolean(false)
    val relief = ObservableBoolean(false)

    constructor(context: Context, mView: ILayerManager) : this() {
        mContext = context
        this.mView = mView
    }

    /**
     * 显示图层 [type] 图层类型 1基础图 2影像图 3地形图
     */
    fun showLayer(type: Int) {
        mView.showLayer(type)
    }

    fun setExtent(){

    }
    /**
     * 关闭窗口
     */
    fun closeView() {
        mView.close()
    }
}