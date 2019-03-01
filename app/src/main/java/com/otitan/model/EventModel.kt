package com.otitan.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient

/**
 * 事件上传
 */
@Entity
class EventModel {
    @Id
    //本地数据库用户id 自增长
    var locId: Long? = null
    //主键标识
    var ID: String? = null
    //用户标识
    var USERID: String? = null
    //上传时间
    var CJTIME: String? = null
    //经度
    var LON: String = ""
    //纬度
    var LAT: String = ""
    //事件描述
    var SJMS: String = ""
    //事件类型（0：森林火灾，1：森林病虫害，2：偷拉盗运，3：乱砍滥伐，4：征占用林地，5：捕杀野生动物）
    var SJLX: Int = -1
    //发生地点
    var FSD: String = ""
    //联系方式
    var LXFS: String = ""
    //备注
    var BZ: String = ""
    //附件列表
    @Transient
    var FJS: List<Att> = ArrayList()

    class EventResult {
        var locId: Long = -1
        //主键标识
        var ID: String? = null
        //用户标识
        var USERID: String? = null
        //上传时间
        var CJTIME: String? = null
        //经度
        var LON: String = ""
        //纬度
        var LAT: String = ""
        //事件描述
        var SJMS: String = ""
        //事件类型（0：森林火灾，1：森林病虫害，2：偷拉盗运，3：乱砍滥伐，4：征占用林地，5：捕杀野生动物）
        var SJLX: String = ""
        //发生地点
        var FSD: String = ""
        //联系方式
        var LXFS: String = ""
        //备注
        var BZ: String = ""
        //附件列表
        var FJS: List<Att> = ArrayList()
    }

    //附件信息
//    @Entity
    class Att {
        //本地数据库id
//        @Id
        var locId: Long? = null
        //本地数据库父级id
        var gId: Long? = null
        //主键标识
        var ID: String? = null
        //	附件类型（1：图片，2：视频，3：音频）
        var FJLX: Int = 0
        //	文件名称
        var WJM: String = ""
        //	文件完整路径名
        var WZWJM: String = ""
        //上传时间
        var CJTIME: String? = null
    }
}