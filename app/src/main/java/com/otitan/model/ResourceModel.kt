package com.otitan.model

import java.io.Serializable

/**
 * 资源管理
 */
class ResourceModel : Serializable {

    /**
     * 林地面积
     */
    inner class LDMJModel : Serializable {
        //地区编码
        var Code: String? = null
        //地区名称
        var Name: String? = null
        //林地总面积  林地面积(数据管理)
        var Area: String? = null
        //乔木林地
        var QMLD: String? = null
        //竹林地
        var ZLD: String? = null
        //疏林地
        var SLD: String? = null
        //	特殊灌木林地
        var TSGMLD: String? = null
        //	一般灌木林地
        var YBGMLD: String? = null
        //	未成林造林地
        var WCLZLD: String? = null
        //宜林地
        var YLD: String? = null
        //	其它
        var Other: String? = null
        //年份(数据管理)
        var Year: String? = null
        //校正面积(数据管理)
        var CorrectionArea: String? = null
    }

    /**
     * 森林面积
     */
    inner class SLMJModel : Serializable {
        //地区编码
        var Code: String? = null
        //地区名称
        var Name: String? = null
        //林地总面积
        var Area: String? = null
        //年份(数据管理)
        var Year: String? = null
        //森林面积(数据管理)
        var TotalArea: String? = null
        //校正面积(数据管理)
        var TotalCorrectionArea: String? = null
    }

    /**
     * 公益林面积
     */
    inner class GYLMJModel : Serializable {
        //地区编码
        var Code: String? = null
        //地区名称
        var Name: String? = null
        //国家级公益林
        var CountryArea: String? = null
        //地方公益林
        var LocalArea: String? = null
        //合计
        var Area: String? = null
        //年份(数据管理)
        var Year: String? = null
        //公益林总面积(数据管理)
        var TotalArea: String? = null
    }

    /**
     * 森林覆盖率
     */
    inner class SLFGLModel : Serializable {
        //地区编码
        var Code: String? = null
        //地区名称
        var Name: String? = null
        //森林覆盖率
        var Area: Double = 0.0
        //年份(数据管理)
        var Year: String? = null
        //森林覆盖率(数据管理)
        var Per: String? = null
        //校正森林覆盖率(数据管理)
        var CorrectPer: String? = null

    }

    /**
     * 活立木蓄积
     */
    inner class HLMXJModel : Serializable {
        //地区编码
        var Code: String? = null
        //地区名称
        var Name: String? = null
        //活立木蓄积
        var Area: Double = 0.0
        //活立木蓄积(数据管理)
        var Standing: Double = 0.0
        //校正活立木蓄积(数据管理)
        var CorrectStanding: Double = 0.0
    }
}
