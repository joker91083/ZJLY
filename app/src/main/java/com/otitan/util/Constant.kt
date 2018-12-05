package com.otitan.util

import android.Manifest
import java.text.DecimalFormat

//存放项目使用的常量数据

object Constant{


    val TAG = "浙江林业二期=="

    /** 动态获取权限 */
    val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE)
    /*存储*/
    val STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val LOCATION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

    val CAMERA = arrayOf(
            Manifest.permission.CAMERA)
    //麦克风
    val AUDIO = arrayOf(
            Manifest.permission.RECORD_AUDIO)

    val PERMISSIONS_REQUEST_CODE = 0 // 请求码

    val PERMISSIONS_GRANTED = 0 // 权限授权
    val PERMISSIONS_DENIED = 1 // 权限拒绝
    val PICSEL = 2//选择图片


    val filePath = "文件可用地址"
    val fileNotFound = "文件不存在"
    val defalutScale = 3000.0

    val tFormat = DecimalFormat(".00")
    val sFormat = DecimalFormat("0.000000")

}


