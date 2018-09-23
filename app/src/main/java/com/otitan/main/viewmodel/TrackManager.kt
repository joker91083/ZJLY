package com.otitan.main.viewmodel


/**
 * Created by hanyw on 2018/5/30
 * 轨迹管理
 */
interface TrackManager{
    /**
     * 日期选择
     */
    fun showDateSelect(type: Int)

    /**
     * 隐藏对话框
     */
    fun closeDialog()

    /**
     * 绘制轨迹
     */
    fun drawTrack(trackpoints:Any?)
}