package com.otitan.model

import java.io.Serializable

/**
 * 森林公园
 */
class SlgyModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //地区名称
    var Name: String = ""
    //造总个数
    var Count: Int = 0
    //总计面积
    var Area: Double = 0.0
    //地区编号
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
        //区县编码
        var DistributeCode: String = ""
        //区县名称
        var DistributeName: String = ""
        //excel中导入的区县名称
        var OrginDistributName: String = ""
        //森林公园名称
        var ForestParkingName: String = ""
        //建园时间
        var CreateParkTime: String = ""
        //批复文号
        var No: String = ""
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