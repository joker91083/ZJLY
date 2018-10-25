package com.otitan.model

import java.io.Serializable

/**
 * 国有林场
 */
class GylcModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //地区名称
    var Name: String = ""
    //总个数
    var Count: Int = 0
    //总计经营面积
    var Area: Double = 0.0
    //地区编码
    var Code: String = ""

    /**
     * 数据管理
     */
    class Sjgl {
        //主键
        var Id: String = ""
        //市编码
        var CityCode: String = ""
        //市名称
        var CityName: String = ""
        //国有林场名称
        var ForestFarmName: String = ""
        //面积
        var Area: String = ""
        //备注
        var Remark: String = ""
        //经度
        var Longitude: String = ""
        //纬度
        var Latitude: String = ""
    }
}