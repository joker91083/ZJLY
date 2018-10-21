package com.otitan.model

import java.io.Serializable

/**
 * 国有林场
 */
class GylcModel : Serializable {
    //地区名称
    var Name: String = ""
    //总个数
    var Count: Int = 0
    //总计经营面积
    var Area: Double = 0.0
    //地区编码
    var Code: String = ""
}