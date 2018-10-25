package com.otitan.model

import java.io.Serializable

/**
 * 湿地保护
 */
class SdbhModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //地区编码
    var Code: String = ""
    //地区名称
    var Name: String = ""
    //总个数
    var Count: Int = 0
    //总面积
    var AllArea: Double = 0.0
    //湿地面积
    var WetArea: Double = 0.0
    //自然湿地面积
    var NaturalWetArea: Double = 0.0

    class Sjgl {
        //主键
        var Id: String? = null
        //市编码
        var CityCode: String? = null
        //市名称
        var CityName: String? = null
        //区县编码
        var DistributeCode: String? = null
        //区县名称
        var DistributeName: String? = null
        //湿地名称
        var Name: String? = null
        //保护形式
        var ProType: String? = null
        //级别
        var Grade: String? = null
        //保护区类型
        var ProTypeName: String? =null
        //是否重点
        var IsPoint: String? = null
        //总面积
        var AllArea: String? = null
        //湿地面积
        var WetArea: String? = null
        //自然湿地面积
        var NaturalWetArea: String? = null
        //主要保护对象
        var ProObject: String? = null
        //建立时间
        var CreateTime: String? = null
        //主管单位
        var Unit: String? = null
    }
}