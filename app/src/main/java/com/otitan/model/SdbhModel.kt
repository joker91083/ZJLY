package com.otitan.model

import java.io.Serializable

/**
 * 湿地保护
 */
class SdbhModel : Serializable {
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
}