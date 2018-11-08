package com.otitan.model

import java.io.Serializable

/**
 * 林权
 */
class LQuanModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    class Xxzl {
        //地区编码
        var code: String = ""
        //地区名称
        var name: String = ""
        //数量
        var count: String = ""
    }

    class Sjgl {
        /**林权证本*/
        //林权证编号
        var Number: String = ""
        //林证字_年
        var LqYear: String = ""
        //签名摘要
        var Qmzy: String = ""
        //时间戳
        var Sjc: String = ""
        //签名值
        var Qmz: String = ""
        //证号类型
        var Kind: String = ""
        //状态
        var State: String = ""
        //页数
        var UsedPag: String = ""
        //发证时间
        var AwardTime: String = ""
        //发证时间 文字版
        var AwardTimeText: String = ""
        //林证字_号
        var LqCode: String = ""
        //林证字
        var LqWord: String = ""
        //批次
        var BatcString = ""
        //行政名称
        var CodeName: String = ""
        //行政代码
        var AdminCode: String = ""
        //签名证书公钥
        var Qmzsgy: String = ""


        /**登记表*/
        //页码
        var Page: String = ""
        //南
        var South: String = ""
        //西
        var West: String = ""
        //东
        var East: String = ""
        //北
        var North: String = ""
        //经办人
        var HandlePerson: String = ""
        //经办人_日期
        var HandlePersonDate: String = ""
        //经办人_日期 文字
        var HandlePersonDateText: String = ""
        //负责人
        var ChargePerson: String = ""
        //负责人_日期
        var ChargePersonDate: String = ""
        //负责人_日期 文字
        var ChargePersonDateText: String = ""
        //身份证
        var IdCard: String = ""
        //注记
        var Zj: String = ""
        //审批状态
        var ApprovalMode: String = ""
        //状态
        var ValidMode: String = ""
        //面积单位
        var AreaUnit: String = ""
        //林种
        var LinZhong: String = ""
        //株数
        var ZhuShu: String = ""
        //树种
        var ShuZhong: String = ""
        //使用期
        var UseTime: String = ""
        //终止日期
        var EndDate: String = ""
        //村编号
        var VillageId: String = ""
        //面积
        var Area: String = ""
        //小班
        var XiaoBan: String = ""
        //林权证编号
        var LQCardId: String = ""
        //组名
        var GroupName: String = ""
        //组编号
        var GroupId: String = ""
        //村名
        var VillageName: String = ""
        //乡镇名
        var TownName: String = ""
        //镇编号
        var TownId: String = ""
        //行政代码
        var CodeId: String = ""
        //单位
        var Unit: String = ""
        //法人
        var Corporation: String = ""
        //户
        var Door: String = ""
        //林班
        var LinBan: String = ""
        //小地名
        var SmallArea: String = ""
        //林地所有权权利人
        var Obligee: String = ""
        //座落
        var Stand: String = ""
        //林木使用权
        var WordUsufruct: String = ""
        //林地使用权
        var TerraUsufruct: String = ""
        //林木所有权
        var WoodFee: String = ""
        //林地所有权
        var ForestFee: String = ""
        //登记类型
        var BookType: String = ""

    }
}