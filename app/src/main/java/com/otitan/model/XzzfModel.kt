package com.otitan.model

import java.io.Serializable

/**
 * 行政执法
 */
class XzzfModel : Serializable {
    //地区编码
    var Code: String = ""
    //地区名称
    var Name: String = ""
    //合计
    var Count: Int = 0
    //年份
    var Year: Int = 0
    //森林火灾案
    var SLHZA: Int = 0
    //毁坏林木、苗木
    var HHLM: Int = 0
    //滥伐林木
    var LFLM: Int = 0
    //滥伐森林或者其他林木
    var LFLMQT: Int = 0
    //盗伐林木
    var DFLM: Int = 0
    //违反森林植物检疫规定
    var WFSL: Int = 0
    //违法征、占用林地
    var WFZ: Int = 0
    //违法收购、运输木材
    var WFSG: Int = 0
    //非法收购、出售、运输野生动物及其产品
    var FFSG: Int = 0
    //非法经营、加工木材
    var FFJY: Int = 0
    //其他
    var Other: Int = 0
}