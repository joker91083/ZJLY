package com.otitan.ui.vm

import android.content.Context
import com.esri.arcgisruntime.mapping.view.SketchEditor
import com.esri.arcgisruntime.mapping.view.SketchStyle
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.IMapTool
import org.jetbrains.anko.toast
import kotlin.properties.Delegates


class MapToolViewModel() : BaseViewModel() {

    var mView: IMapTool by Delegates.notNull()
    //草图编辑器
    var sketchEditor: SketchEditor by Delegates.notNull()

    constructor(context: Context, mView: IMapTool) : this() {
        mContext = context
        this.mView = mView
    }

    //地图信息
    fun showInfo() {
        mContext?.toast("图层信息")
    }

    //测距
    fun getDistance() {
        mContext?.toast("测距")
    }

    //测面
    fun getArea() {
        mContext?.toast("测面")
    }

    //清除标绘
    fun onClean() {
        mContext?.toast("清除")
    }

    //定位当前位置
    fun getLocation() {
        mContext?.toast("当前位置")
        mView.myLocation()
    }

    fun initSketchEditor() {
        sketchEditor = SketchEditor()
        sketchEditor.sketchStyle = SketchStyle()
    }
}