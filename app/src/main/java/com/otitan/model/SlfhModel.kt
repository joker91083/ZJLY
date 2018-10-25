package com.otitan.model

/**
 * 森林防火
 */
class SlfhModel<T> {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    /**
     * 火灾次数
     */
    class Hzcs {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //火灾合计
        var TotalFireCount: Int = 0
        //一般火灾
        var NormalFire: Int = 0
        //较大火灾
        var LargeFire: Int = 0
        //重大火灾
        var MajorFire: Int = 0
        //特大火灾
        var OversizeFire: Int = 0
    }

    /**
     * 火场面积
     */
    class Hcmj {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //火场面积
        var TotalFireCount: Double = 0.0
    }

    /**
     * 损失面积
     */
    class Ssmj {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //损失面积
        var TotalFireCount: Double = 0.0
    }

    /**
     * 扑火经费
     */
    class Phjf {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //扑火经费
        var TotalFireCount: Double = 0.0
    }

    /**
     * 人员伤亡
     */
    class Rysw {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //合计
        var TotalCount: Int = 0
        //轻伤
        var Minor: Int = 0
        //重伤
        var Serious: Int = 0
        //死亡
        var Death: Int = 0
    }

    /**
     * 损失林木
     */
    class Sslm {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //成林蓄积(m³)
        var Accumulation: Double = 0.0
        //幼苗株数(万株)
        var Seedling: Double = 0.0
    }

    /**
     * 数据管理
     */
    class Sjgl{
        //年份
        var Year:Int=0
        //市
        var CityName:String?=null
        //区县
        var DistrictName:String?=null
        //乡
        var VillageName:String?=null
        //火灾名称
        var Remark:String?=null
        //火灾等级
        var FireGrade:String?=null
        //火场面积
        var FireArea:Double=0.0
        //损失面积
        var LossArea:Double=0.0
        //扑火经费
        var Money:Double=0.0
        //轻伤人员
        var MinorCount:Int=0
        //校正轻伤人员
        var CorrectMinorCount:String?=null
        //重伤人员
        var SeriousCount:Int=0
        //校正重伤人员
        var CorrectSeriousCount:String?=null
        //死亡人员
        var DeathCount:Int=0
        //校正死亡人员
        var CorrectDeathCount:String?=null
        //成林蓄积
        var ForestAccumulation:Double=0.0
        //校正成林蓄积
        var CorrectForestAccumulation:String?=null
        //登记时间
        var RecordTime:String?=null
        //火灾编号
        var FireNo:String?=null
        //火灾开始时间
        var StartTime:String?=null
        //火灾扑灭时间
        var EndTime:String?=null
        //经度(度)
        var JDD:Int=0
        //经度(分)
        var JDF:Int=0
        //经度(秒)
        var JDM:Int=0
        //纬度(度)
        var WDD:Int=0
        //纬度(分)
        var WDF:Int=0
        //纬度(秒)
        var WDM:Int=0
        //坡度
        var PD:Double=0.0
        //火险等级
        var HXDJ:Int=0
        //过火面积
        var GHMJ:Double=0.0
        //受害森林面积
        var SHSLMJ:Double=0.0
        //天然林面积
        var TRLMJ:Double=0.0
        //人工林面积
        var RGLMJ:Double=0.0
        //起火详细原因
        var QHXXYY:String?=null
        //肇事者姓名
        var ZSZXM:String?=null
        //肇事者性别
        var ZSZXB:String?=null
        //肇事者年龄
        var ZSZNL:Int=0
        //处理详细结果
        var CLXXJG:String?=null
        //扑火过程简述及经验教训
        var GCJY:String?=null
        //起火原因
        var QHYY:String?=null
        //上报人
        var SBR:String?=null
        //调查人
        var DCR:String?=null
        //幼苗株数
        var Seedling:Double=0.0
        //校正幼苗株数
        var CorrectSeedling:Double?=0.0
    }
}