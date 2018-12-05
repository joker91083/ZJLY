package com.otitan.model

import java.io.Serializable

/**
 * 手动定位
 */
class EditLocation : Serializable {
    //经纬度
    //经度
    var lon: String = ""
    //纬度
    var lat: String = ""

    //度分秒类型
    //经度-度
    var lonD: String = ""
    //经度-分
    var lonF: String = ""
    //经度-秒
    var lonM: String = ""
    //纬度-度
    var latD: String = ""
    //纬度-分
    var latF: String = ""
    //纬度-秒
    var latM: String = ""

    //米制
    var x: String = ""
    var y: String = ""

    //当前位置坐标
    var locLon: String = ""
    var locLat: String = ""
}