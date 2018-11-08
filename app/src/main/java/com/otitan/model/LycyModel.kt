package com.otitan.model

import java.io.Serializable

/**
 * 林业产业
 */
class LycyModel<T> : Serializable {
    //总记录数
    var total: Int = 0
    //请求返回的数据列表
    var rows = ArrayList<T>()

    //总产业
    class Zcy : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //第一产业产值
        var First: Double = 0.0
        //第二产业产值
        var Second: Double = 0.0
        //第三产业产值
        var Third: Double = 0.0
    }

    //第一产业
    class Dycy : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //林木育种和育苗
        var lmyzym: Double = 0.0
        //营造林
        var yzl: Double = 0.0
        //木材和竹材采运
        var mczccy: Double = 0.0
        //经济林产品的种植与采集
        var jjlcpzzcj: Double = 0.0
        //花卉及其他观赏植物种植
        var hhjqtgszwzz: Double = 0.0
        //陆生野生动物繁育与利用
        var lsysdwfyly: Double = 0.0
        //其他
        var qt: Double = 0.0
    }

    //第二产业
    class Decy : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //木质工艺品和木质文教体育用品制造
        var mztzwzpzz: Double = 0.0
        //木竹藤家具制造
        var mztjjzz: Double = 0.0
        //木竹苇浆造纸和纸制品
        var mzwjzzzp: Double = 0.0
        //林产化学产品制造
        var lchxcpzz: Double = 0.0
        //木材加工和木竹藤棕苇制品制造
        var mzgypmzwjtyypzz: Double = 0.0
        //非木质林产品加工制造业
        var fmzlcpjgzzy: Double = 0.0
        //其他
        var qt: Double = 0.0
    }

    //第三产业
    class Dscy : Serializable {
        //地区编码
        var Code: String = ""
        //地区名称
        var Name: String = ""
        //林业生产服务
        var lyscfw: Double = 0.0
        //林业旅游与休闲服务
        var lylyyxxfw: Double = 0.0
        //林业生态服务
        var lystfw: Double = 0.0
        //林业专业技术服务
        var lyzyjsfw: Double = 0.0
        //林业公共管理及其他组织服务
        var lygggljqtzzfw: Double = 0.0
        //其他
        var qt: Double = 0.0
    }

    /**
     * 数据管理
     */
    class Sjgl {
        //年份
        var YEAR: String = ""
        //地区名称
        var DQMC: String = ""
        //地区编码
        var DQCODE: String = ""
        //总产值
        var ZJ_ZCZ: String = ""
        //第一产业产值
        var DYCY_ZJ_ZCZ: String = ""
        //第二产业产值
        var DECY_ZJ_ZCZ: String = ""
        //第三产业产值
        var DSCY_ZJ_ZCZ: String = ""
    }
}