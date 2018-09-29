package com.otitan.model

import java.io.Serializable

class BaseLayer:Serializable{
    //基础图 影像图 地形图
    var name:String=""
    //1基础图 2影像图 3地形图
    var type:Int=0
}