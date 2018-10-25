package com.otitan.model

import java.io.Serializable

/**
 * 林地征占
 */
class LdzzModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //项目个数
    class Xmgs : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //项目个数
        var Count: Int = 0
    }

    //征占用林地面积
    class Zzyldmj : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //征占用地总面积
        var Total: Double = 0.0
        //防护林
        var FHL: Double = 0.0
        //特用林
        var TYL: Double = 0.0
        //用材林
        var YCL: Double = 0.0
        //经济林
        var JJL: Double = 0.0
        //薪炭林
        var XTL: Double = 0.0
        //用材(经济,薪炭)林采伐迹地
        var YCLCFJD: Double = 0.0
        //苗圃地
        var MPD: Double = 0.0
        //未成林造地
        var WCLZD: Double = 0.0
        //疏林地,灌木林地
        var SLD: Double = 0.0
        //其他
        var QT: Double = 0.0
    }

    //林地定额
    class Ldde : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //林地定额
        var Count: Int = 0
    }

    /**
     * 数据管理
     */
    class Sjgl {
        //年份
        var Year: String = ""
        //编号
        var ApplicationId: String = ""
        //类型
        var AreaType: String = ""
        //项目批准机关
        var ApprovalAuthority: String = ""
        //项目名称
        var ProjectName: String = ""
        //审批权限
        var Approve: String = ""
        //批准文号
        var ApprovalNumber: String = ""
        //森林植被恢复费
        var ShouldForestVegetationRestorationCosts: Int = 0
    }
}