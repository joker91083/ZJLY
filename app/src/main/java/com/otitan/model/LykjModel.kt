package com.otitan.model

import java.io.Serializable

class LykjModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //省院合作项目
    class Syhzxm : Serializable {
        //主键
        var Id: String = ""
        //项目名称
        var Name: String = ""
        //项目编号
        var No: String = ""
        //承担单位
        var Unit: String = ""
        //主持人
        var HostUser: String = ""
        //总经费
        var Funds: Double = 0.0
        //财政补助
        var Subsidy: Double = 0.0
    }

    //示范资金项目
    class Sfzjxm : Serializable {
        //主键
        var Id: String = ""
        //项目编号
        var No: String = ""
        //项目名称
        var Name: String = ""
        //承担单位
        var Unit: String = ""
        //主持人
        var HostUser: String = ""
        //技术支撑单位
        var BraceUnit: String = ""
        //总经费
        var Funds: Double = 0.0
        //财政补助
        var Subsidy: Double = 0.0
        //年份
        var Year: Double = 0.0
        //备注
        var Reamrk: String = ""
    }

    //国家标准
    class Gjbz : Serializable {
        //主键
        var Id: String = ""
        //中文标准名称
        var Name: String = ""
        //标准号
        var No: String = ""
        //发布日期
        var PubTime: String = ""
        //实施日期
        var ImplementTime: String = ""
        //主管部门
        var Dep: String = ""
        //起草单位
        var DraftUnit: String = ""
        //归口单位
        var PutUnit: String = ""
        //文件名
        var File: String = ""
    }

    //行业标准
    class Hybz : Serializable {
        //主键
        var Id: String = ""
        //中文标准名称
        var Name: String = ""
        //标准号
        var No: String = ""
        //发布日期
        var PubTime: String = ""
        //起草单位
        var DraftUnit: String = ""
        //归口单位
        var PutUnit: String = ""
        //文件名
        var File: String = ""
    }

    //省级地方标准
    class Sjdfbz : Serializable {
        //主键
        var Id: String = ""
        //标准号
        var No: String = ""
        //中文标准名称
        var Name: String = ""
        //起草性质
        var Nature: String = ""
        //起草单位
        var DraftUnit: String = ""
        //备注
        var Remark: String = ""
        //文件名
        var File: String = ""
    }

    //浙江制造标准
    class Zjzzbz : Serializable {
        //主键
        var Id: String = ""
        //中文标准名称
        var Name: String = ""
        //标准号
        var No: String = ""
        //时间
        var Time: String = ""
        //地区
        var Area: String = ""
        //文件名
        var File: String = ""
    }
}