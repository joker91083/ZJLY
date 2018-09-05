package com.otitan.base

interface IBaseViewModel {
    /**
     * View的界面创建时回调
     */
    fun onCreate()

    /**
     * View的界面销毁时回调
     */
    fun onDestroy()
}