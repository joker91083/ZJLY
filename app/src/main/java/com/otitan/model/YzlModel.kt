package com.otitan.model

import java.io.Serializable

class YzlModel : Serializable {
    //地区名称
    var name: String = ""
    //造林面积
    var value: String = ""
    //地区编号
    var code: String = ""

    /**
     * 数据管理 完成情况
     */
    class Wcqk {
        //主键
        var Id: String = ""
        //地区编码
        var Code: String = ""
        //年份
        var Year: String? = ""
        //地区名称
        var AdminstativeName: String = ""
        //造林面积
        var Area: String? = ""
        //更新造林面积
        var CorrectArea: String? = ""
        //人工造林面积
        var ReforestationArea: String? = ""
        //更新人工造林面积
        var CorrectReforestationArea: String? = ""
        //(迹地)更新面积
        var SlashArea: String? = ""
        //更新(迹地)更新面积
        var CorrectSlashArea: String? = ""
        //森林抚育
        var TendingArea: String? = ""
        //更新森林抚育
        var CorrectTendingArea: String? = ""
        //年末实有封山(沙)育林面积
        var SealingArea: String? = ""
        //更新年末实有封山(沙)育林面积
        var CorrectSealingArea: String? = ""
        //四旁(零星)植树
        var ThourTree: String? = ""
        //更新四旁(零星)植树
        var CorrectThourTree: String? = ""
        //育苗产量
        var NurseryStock: String? = ""
        //更新育苗产量
        var CorrectNurseryStock: String? = ""
        //育苗面积
        var GrowArea: String? = ""
        //更新育苗面积
        var CorrectGrowArea: String? = ""
    }

    /**
     * 数据管理 计划任务
     */
    class Jhrw {
        //主键
        var Id: String = ""
        //地区编码
        var Code: String = ""
        //年份
        var Year: String? = null
        //地区名称
        var AdminstativeName: String = ""
        //造林面积
        var Area: String? = null
        //更新造林面积
        var CorrectArea: String? = null
        //人工造林面积
        var ReforestationArea: String? = null
        //更新人工造林面积
        var CorrectReforestationArea: String? = null
        //封山育林
        var SealingArea: String? = null
        //更新封山育林面积
        var CorrectSealingArea: String? = null
        //森林抚育
        var ForestTendingArea: String? = null
        //更新森林抚育面积
        var CorrectForestTendingArea: String? = null
        //低效林改造
        var LowForestArea: String? = null
        //更新低效林改造
        var CorrectLowForestArea: String? = null
        //新育苗面积
        var NewBreedingArea: String? = null
        //更新新育苗面积
        var CorrectNewBreedingArea: String? = null
        //四旁(零星)植树
        var ThourTree: String? = null
        //更新四旁(零星)植树
        var CorrectThourTree: String? = null
    }
}