package com.otitan.model

import com.esri.arcgisruntime.data.FeatureTable
import com.esri.arcgisruntime.layers.FeatureLayer
import java.io.Serializable

class MyLayer:Serializable{
    //gdb数据文件夹名称
    var pName:String=""
    //gdb文件名称
    var cName:String=""
    //gdb文件地址
    var path:String=""
    //gdb子图层名称
    var lName:String=""
    //子图层
    var layer:FeatureLayer?=null
    var table: FeatureTable?=null
}