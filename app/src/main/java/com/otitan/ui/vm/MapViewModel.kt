package com.otitan.ui.vm

import android.content.Context
import android.view.View
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.IMap
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

class MapViewModel(val context: Context) : BaseViewModel() {
    /*arcgismap*/
    private var mView: IMap? = null
    var layerManagerViewModel:LayerManagerViewModel by Delegates.notNull()
    var imgManagerViewModel:ImgManagerViewModel by Delegates.notNull()
    var mapToolViewModel:MapToolViewModel by Delegates.notNull()

    //主页面中的子view集合
    val viewMap = HashMap<String, View>()

    constructor(context: Context, mapModel: IMap) : this(context) {
        this.mView = mapModel
        mContext = context
    }

    /**
     * 图层信息
     */
    fun showInfo() {
        mContext?.toast("图层信息")
    }

    /**
     * 测距
     */
    fun getDistance() {
        mContext?.toast("测距")
    }

    /**
     * 勾绘
     */
    fun onSketch() {
        mContext?.toast("勾绘")
    }

    /**
     * 清除编辑
     */
    fun onClean() {
        mContext?.toast("清除编辑")
    }

    /**
     * 当前位置
     */
    fun getLocation() {
        mContext?.toast("当前位置")
        //mView?.myLocation()
    }

    /**
     * 图层控制
     */
    fun layerControl() {
        mView?.showTckz()
    }

    /**
     * GPS设置
     */
    fun systemSetting() {
        mContext?.toast("GPS设置")
    }

    /**
     * 数据采集
     */
    fun dataCollection() {
        mContext?.toast("数据采集")
    }

    /**
     * 小班编辑
     */
    fun classEditor() {
        mContext?.toast("小班编辑")
    }

    fun closeView(viewName: String) {
        val view = viewMap[viewName]
        if (view != null) {
            view.visibility = View.GONE
        }
    }



}