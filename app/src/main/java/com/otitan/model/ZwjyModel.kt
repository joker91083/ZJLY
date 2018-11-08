package com.otitan.model

import java.io.Serializable

/**
 * 植物检疫
 */
class ZwjyModel<T> : Serializable {
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
        /**国内调运检疫报检单*/
        //审批地区
        var AREACODE: String = ""
        //报检单编号
        var BJDBH: String = ""
        //调运单位
        var DYNAME: String = ""
        //承运人姓名
        var CYNAME: String = ""
        //运输工具
        var YSNAME: String = ""
        //报检人
        var NOKEY: String = ""
        //申请编号
        var SQID: String = ""
        //审批地区
        var SPAREA: String = ""
        //申请日期
        var SQDATE: String = ""
        //申请日期文字
        var SQDATEText: String = ""
        //调出日期
        var DCDATE: String = ""
        //调出日期文字
        var DCDATEText: String = ""
        //企业编号
        var QYID: String = ""
        //调运单位地址
        var DYADD: String = ""
        //承运人身份证号
        var CYNO: String = ""
        //承运人联系电话
        var CYTEL: String = ""
        //收货单位
        var SHNAME: String = ""
        //收货单位地址
        var SHADD: String = ""
        //收货单位电话
        var SHTEL: String = ""
        //植物或植物产品来源
        var PLSOURCE: String = ""
        //目的地地区代码
        var TOAREA: String = ""
        //起运地
        var YSFROM: String = ""
        //经过地
        var YSAT: String = ""
        //目的地
        var YSTO: String = ""
        //调运总数量
        var HWCOUNT: String = ""
        //调运总货值
        var HWAMOUNT: String = ""
        //是否省外
        var ISOUT: String = ""
        //存放地点
        var CFDD: String = ""
        //备注
        var BZ: String = ""
        //收货地区代码
        var SHAREA: String = ""
        //报检单位联系人
        var LXMAN: String = ""
        //报检单位联系人手机号码
        var LXPHONE: String = ""


        /**植物检疫证书信息*/
        //证书流水号
        var HGZID: String = ""
        //省内还是省外
        var IFSW: String = ""
        //签证省份简称
        var JYZXH: String = ""
        //证书编号
        var ZSNO: String = ""
        //有效期起
        var DATEFROM: String = ""
        //有效期起文字
        var DATEFROMText: String = ""
        //有效期止
        var DATETO: String = ""
        //有效期止文字
        var DATETOText: String = ""
        //签发单位意见
        var QFRMK: String = ""
        //委托机关
        var WTNAME: String = ""
        //签发机关
        var QFNAME: String = ""
        //签发时间
        var QFDATE: String = ""
        //签发时间文字
        var QFDATEText: String = ""
        //检疫员
        var QUARNAME: String = ""
        //作废原因
        var ZFWHY: String = ""
        //作废人员
        var ZFMAN: String = ""
        //作废时间
        var ZFDATE: String = ""
        //作废时间文字
        var ZFDATEText: String = ""
        //签发状态
        var QFST: String = ""
        //审核依据
        var SHTYPE: String = ""
        //原单号
        var SHNO: String = ""


        /**产地检疫报检*/
        //申请表编号
        var SQBBH: String = ""
        //申请单位/个人
        var QYNAME: String = ""
        //联系人姓名
        var LXRNAME: String = ""
        //联系人电话
        var LXRTEL: String = ""
        //联系人身份证号
        var LXRBODYNO: String = ""
        //申请单位地址
        var QYADD: String = ""
        //产地检疫地点
        var SCADD: String = ""
        //种植面积
        var SCAREA: String = ""
        //数量单位
        var SCDW: String = ""
        //预定运往地点
        var YDYW: String = ""
        //预定起运时间
        var YDDATE: String = ""
        //检疫员编号
        var QUARID: String = ""


        /**产地检疫合格证*/
        //合格证流水号
        var HZDID: String = ""
        //省名
        var PRIVNCE: String = ""
        //县名
        var COUNTY: String = ""
        //年度
        var NYEAR: String = ""
        //合格证序号
        var HGZXH: String = ""
        //植物及产品名称
        var PLANTNAME: String = ""
        //数量
        var AMOUNT: String = ""
        //产地检疫地点
        var JYADD: String = ""
        //预定起运时间
        var QYDATE: String = ""
        //预定起运时间文字
        var QYDATEText: String = ""
        //预定运往地点
        var TOADD: String = ""
        //签发机关
        var COMNAME: String = ""
        //合格证状态
        var HGZST: String = ""
        //备注
        var REMARK: String = ""
    }
}