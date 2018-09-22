package com.otitan.ui.vm

import android.content.Context
import android.util.Log
import com.esri.arcgisruntime.mapping.view.SketchEditor
import com.esri.arcgisruntime.mapping.view.SketchStyle
import com.otitan.base.BaseViewModel
import com.otitan.util.ToastUtil
import kotlin.properties.Delegates


class MapToolViewModel() : BaseViewModel() {

    constructor(context: Context) :this(){
        this.mContext = context
    }

    //草图编辑器
    var sketchEditor: SketchEditor by Delegates.notNull()

    //地图信息
    public fun showInfo() {
        Log.e("============","地图信息")
        ToastUtil.setToast(this.mContext,"地图信息")
    }

    //测距
    public fun getDistance() {
        ToastUtil.setToast(this.mContext,"测距")
    }

    //测面
    public fun getArea() {
        ToastUtil.setToast(this.mContext,"测面")
    }

    //清除标绘
    public fun onClean() {
        ToastUtil.setToast(this.mContext,"清除标绘")
    }

    //定位当前位置
    public fun getLocation() {
        ToastUtil.setToast(this.mContext,"定位当前位置")
    }

    public fun initSketchEditor(){
        sketchEditor = SketchEditor()
        sketchEditor.sketchStyle = SketchStyle()
    }
}