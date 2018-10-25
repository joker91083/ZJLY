package com.otitan.model

import java.io.Serializable

/**
 * 有害生物
 */
class YhswModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //主键
    var Id: String? = null
    //年份
    var Year: Double? = null
    //地区编码
    var Code: String? = null
    //地区名称
    var AdminstrativeName: String? = null
    //病虫害名称
    var Name: String? = null
    //发生面积
    var HappenArea: Double? = null
    //校正发生面积
    var CorrectHappenArea: Double? = null
    //防治面积
    var PreventionArea: Double? = null
    //校正防治面积
    var CorrectPreventionArea: Double? = null
    //防治作业面积
    var PreventionControlArea: Double? = null
    //校正防治作业面积
    var CorrectPreventionControllerArea: Double? = null
    //成灾面积
    var DisasterArea: Double? = null
    //校正成灾面积
    var CorrectDisasterArea: Double? = null
    //防治费用
    var Money: Double? = null
    //校正防治费用
    var CorrectMoney: Double? = null
    //寄主树种面积
    var Area: Double? = null
    //校正寄主树种面积
    var CorrectArea: Double? = null

    /**
     * 数据管理
     */
    class Sjgl {
        //主键
        var Id: String = ""
        //年份
        var Year: String = ""
        //地区编码
        var Code: String = ""
        //地区名称
        var AdminstrativeName: String = ""
        //病虫害名称
        var Name: String = ""
        //发生面积
        var HappenArea: String = ""
        //校正发生面积
        var CorrectHappenArea: String = ""
        //防治面积
        var PreventionArea: String = ""
        //校正防治面积
        var CorrectPreventionArea: String = ""
        //防治作业面积
        var PreventionControlArea: String = ""
        //校正防治作业面积
        var CorrectPreventionControllerArea: String = ""
        //成灾面积
        var DisasterArea: String = ""
        //校正成灾面积
        var CorrectDisasterArea: String = ""
        //防治费用
        var Money: String = ""
        //校正防治费用
        var CorrectMoney: String = ""
        //寄主树种面积
        var Area: String = ""
        //校正寄主树种面积
        var CorrectArea: String = ""
    }
}