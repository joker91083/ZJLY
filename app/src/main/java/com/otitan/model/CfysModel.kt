package com.otitan.model

import java.io.Serializable

class CfysModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    class Xxzl {
        //地区编码
        var code: String = ""
        //地区名称
        var name: String = ""
        //type=1 林木采伐证信息
        //count= searchtype =1 证书个数;searchtype =2 采伐面积;searchtype =3 采伐蓄积;searchtype =2 采伐株数
        //type=2 木材运输证信息  count=证书个数
        var count: String = ""
    }

    class Sjgl {
        /**林木采伐证信息*/
        //GUID
        var GUID: String = ""
        //采伐证号
        var CAI_FA_ZH: String = ""
        //申请人
        var SHEN_QIN_REN: String = ""
        //限额类型
        var IS_XIANE: String = ""
        //编限单位名称
        var BIAN_XIAN_DWMC: String = ""
        //编限单位代码
        var BIAN_XIAN_DWDM: String = ""
        //归口单位名称
        var GUI_KOU_DWMC: String = ""
        //归口单位代码
        var GUI_KOU_DWDM: String = ""
        //作业号
        var ZUO_YE_HAO: String = ""
        //林权证号
        var LIN_QUAN_ZH: String = ""
        //林场
        var LIN_CHANG: String = ""
        //工作区
        var GONG_ZUO_QU: String = ""
        //林班
        var LIN_BAN: String = ""
        //小班
        var XIAO_BAN: String = ""
        //小地名
        var XIAO_DI_MING: String = ""
        //四边界东
        var SBJ_DONG: String = ""
        //四边界西
        var SBJ_XI: String = ""
        //四边界南
        var SBJ_NAN: String = ""
        //四边界北
        var SBJ_BEI: String = ""
        //林权名称
        var LIN_QUAN_MC: String = ""
        //林权代码
        var LIN_QUAN_DM: String = ""
        //起源名称
        var QI_YUAN_MC: String = ""
        //起源代码
        var QI_YUAN_DM: String = ""
        //森林类别名称
        var SEN_LIN_LBMC: String = ""
        //森林类别代码
        var SEN_LIN_LB_DM: String = ""
        //林种名称
        var LIN_ZHONG_MC: String = ""
        //林种代码
        var LIN_ZHONG_DM: String = ""
        //树种名称
        var SHU_ZHONG_MC: String = ""
        //树种代码
        var SHU_ZHONG_DM: String = ""
        //采伐类型名称
        var CAI_FA_LXMC: String = ""
        //采伐类型代码
        var CAI_FA_LXDM: String = ""
        //采伐方式名称
        var CAI_FA_FSMC: String = ""
        //采伐方式代码
        var CAI_FA_FSDM: String = ""
        //树龄名称
        var SHU_LING_MC: String = ""
        //树龄代码
        var SHU_LING_DM: String = ""
        //小班蓄积
        var XIAO_BAN_XJ: String = ""
        //采伐面积
        var CAI_FA_MJ: String = ""
        //面积单位
        var MIAN_JI_DW: String = ""
        //蓄积强度
        var XU_JI_QD: String = ""
        //株数强度
        var ZHU_SHU_QD: String = ""
        //郁闭度
        var YU_BI_DU: String = ""
        //采伐株数
        var CAI_FA_ZS: String = ""
        //采伐期限开始时间
        var CAI_FA_QXQ: String = ""
        //采伐期限开始时间
        var CAI_FA_QXQText: String = ""
        //采伐期限终止时间
        var CAI_FA_QXZ: String = ""
        //采伐期限终止时间
        var CAI_FA_QXZText: String = ""
        //更新方式名称
        var GENG_XING_FSMC: String = ""
        //更新方式代码
        var GENG_XING_FSDM: String = ""
        //更新面积单位
        var GENG_XING_MJDW: String = ""
        //更新面积
        var GENG_XING_MJ: String = ""
        //更新株数
        var GENG_XING_ZS: String = ""
        //更新期限
        var GENG_XING_QX: String = ""
        //更新树种
        var GENG_XING_SZ: String = ""
        //备注
        var BEI_ZHU: String = ""
        //申请时间
        var SHEN_QING_SJ: String = ""
        //申请时间
        var SHEN_QING_SJText: String = ""
        //申请人
        var SHEN_HE_REN: String = ""
        //审核意见
        var SHEN_HE_YJ: String = ""
        //审核时间
        var SHEN_HE_SJ: String = ""
        //审核时间
        var SHEN_HE_SJText: String = ""
        //签发ruid
        var QIAN_FA_RUID: String = ""
        //签发人
        var QIAN_FA_REN: String = ""
        //签发时间
        var QIAN_FA_SJ: String = ""
        //签发时间
        var QIAN_FA_SJText: String = ""
        //状态
        var ZHUANG_TAI: String = ""
        //采伐蓄积
        var CAI_FA_XJ: String = ""
        //采伐出材
        var CAI_FA_CC: String = ""
        //实际采伐株数
        var SJCF_ZHU_SHU: String = ""
        //实际采伐面积
        var SJCF_MIAN_JI: String = ""
        //实际采伐出材
        var SJCF_CHU_CAI: String = ""
        //作废czruid
        var ZUO_FEI_CZRUID: String = ""
        //申请czruid
        var SHEN_QING_CZRUID: String = ""
        //申请人姓名
        var SHEN_QING_CZR: String = ""
        //批准人czruid
        var PI_ZHUN_CZRUID: String = ""
        //批准人姓名
        var PI_ZHUN_CZR: String = ""
        //实际采伐蓄积
        var SJCF_XU_JI: String = ""
        //采伐类型代码1
        var CAI_FA_LXDM1: String = ""
        //采伐类型名称1
        var CAI_FA_LXMC1: String = ""
        //印刷号
        var YIN_SHUA_H: String = ""
        //领证人
        var LING_ZHENG_REN: String = ""
        //作废时间
        var ZUO_FEI_SJ: String = ""
        //作废时间
        var ZUO_FEI_SJText: String = ""
        //采字
        var CAI_ZI: String = ""
        //树种代码
        var SHU_ZHONG_ZDM: String = ""
        //树种名称
        var SHU_ZHONG_ZMC: String = ""
        //其它采伐代码
        var QI_TA_CFDM: String = ""
        //其它采伐名称
        var QI_TA_CFMC: String = ""
        //采伐证号（证上号码）
        var YU_LIU1: String = ""
        //申报者证件类型
        var APPLY_CARDTYPE: String = ""
        //申报者证件号码
        var APPLY_CARDNUMBER: String = ""
        //采伐证类型，1：一般林木  2， 珍贵树木、 3，沿海保护林带
        var TIMBER_TYPE: String = ""

        /**木材运输证信息*/
        //运输证编号
        var YSZ_BH: String = ""
        //货主编号
        var HUO_ZHU_BH: String = ""
        //货主名称
        var HUO_ZHU_MC: String = ""
        //承运人
        var CHENG_YUN_R: String = ""
        //运输方式
        var YUN_SHU_FS: String = ""
        //运输其他方式
        var YUN_SHU_FSQT: String = ""
        //有效期限开始日期
        var YOU_XIAO_Q: String = ""
        //有效期限开始日期文字
        var YOU_XIAO_QText: String = ""
        //有效期限终止
        var YOU_XIAO_Z: String = ""
        //有效期限终止文字
        var YOU_XIAO_ZText: String = ""
        //领证人
        var LING_ZHENG_R: String = ""
        //运输起运地点编码
        var YUN_SHU_QBM: String = ""
        //运输起运地点省名称
        var YUN_SHU_QSMC: String = ""
        //运输起运地点县编码
        var YUN_SHU_QDM: String = ""
        //运输起运地点县名称
        var YUN_SHU_QXMC: String = ""
        //运输终止地点编码
        var YUN_SHU_ZBM: String = ""
        //运输终止地点省编码
        var YUN_SHU_ZSMC: String = ""
        //运输终止地点县编码
        var YUN_SHU_ZXMC: String = ""
        //运输终止地点县名称
        var YUN_SHU_ZDM: String = ""
        //行政编码
        var XZ_CODE: String = ""
        //申请日期
        var SHEN_QING_RQ: String = ""
        //申请日期文字
        var SHEN_QING_RQText: String = ""
        //签发日期
        var QIAN_FA_RQ: String = ""
        //签发日期文字
        var QIAN_FA_RQText: String = ""
        //签发电话
        var QIAN_FA_DH: String = ""
        //来源
        var LAI_YUAN: String = ""
        //流向
        var LIU_XIANG: String = ""
        //作废原因
        var ZUO_FEI_YY: String = ""
        //录入人姓名
        var LU_RU_RXM: String = ""
        //木材产地
        var CHAN_DI: String = ""
    }

}