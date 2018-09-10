package com.otitan.ui.vm

import com.esri.arcgisruntime.mapping.view.SketchEditor
import com.esri.arcgisruntime.mapping.view.SketchStyle
import com.otitan.base.BaseViewModel
import kotlin.properties.Delegates


class MapToolViewModel : BaseViewModel() {

    //草图编辑器
    var sketchEditor: SketchEditor by Delegates.notNull()

    //地图信息
    fun showInfo() {

    }

    //测距
    fun getDistance() {

    }

    //测面
    fun getArea() {

    }

    //清除标绘
    fun onClean() {}

    //定位当前位置
    fun getLocation() {

    }

    fun initSketchEditor(){
        sketchEditor = SketchEditor()
        sketchEditor.sketchStyle = SketchStyle()
    }
}