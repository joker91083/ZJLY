package com.otitan.model

/**
 * 森林防火
 */
class SlfhModel {
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
}