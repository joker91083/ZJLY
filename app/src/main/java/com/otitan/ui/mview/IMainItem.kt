package com.otitan.ui.mview

interface IMainItem {
    /**
     * 设置查询类型
     */
    fun setType(type: String)

    /**
     * 设置选中状态
     */
    fun setSelect(position:Int)

    fun startActivity()
}