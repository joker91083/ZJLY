package com.otitan.zjly.base

/**
 * Created by sp on 2018/6/7.
 */
interface Base {

    /**
     * 提示
     */
    fun showToast(type: Int, msg: String?)

    /**
     * 显示进度弹框
     */
    fun showProgressDialog(title: String, iscancel: Boolean)

    /**
     * 关闭进度弹框
     */
    fun closeProgressDialog()
}