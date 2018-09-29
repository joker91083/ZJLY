package com.otitan.ui.vm

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.ILayerManager
import kotlin.properties.Delegates

class LayerManagerViewModel() : BaseViewModel() {
    private var mView: ILayerManager by Delegates.notNull()
    val base = ObservableBoolean(true)
    val img = ObservableBoolean(false)
    val relief = ObservableBoolean(false)
    val check = ObservableBoolean(false)
    val name = ObservableField<String>()
    var checkedMap: HashMap<Int, Boolean>? = null
    var type: Int = -1


    constructor(context: Context, mView: ILayerManager) : this() {
        mContext = context
        this.mView = mView
    }

    /**
     * 显示图层 [type] 图层类型 1基础图 2影像图 3地形图
     */
    fun showLayer() {
        mView.showLayer(type,check.get())

        checkedMap?.put(type, check.get())
    }

    fun setExtent() {
//        var check = false
//        when (type) {
//            1 -> check = base.get()
//            2 -> check = img.get()
//            3 -> check = relief.get()
//        }
        if (check.get()) {
            mView.setExtent(type)
        }
    }

    /**
     * 关闭窗口
     */
    fun closeView() {
        mView.close()
    }
}