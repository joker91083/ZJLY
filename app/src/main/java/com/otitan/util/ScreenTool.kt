package com.otitan.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager


/**
 * Created by li on 2017/5/31.
 * 设置尺寸类
 */
object ScreenTool {

    fun getScreenPix(context: Context?): Screen {
        val dm = DisplayMetrics()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager?.defaultDisplay?.getMetrics(dm)
        return Screen(dm.widthPixels, dm.heightPixels)
    }

    /**
     * 获取屏幕宽度
     * [leftRight]设定的左右边距
     */
    fun getScreenWidth(context: Context?, leftRight: Int): Int {
        val screen = getScreenPix(context)
        val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftRight.toFloat(), context?.resources?.displayMetrics).toInt()
        return screen.widthPixels - 2*padding
    }

    class Screen {
        var widthPixels: Int = 0

        var heightPixels: Int = 0

        constructor() {}

        constructor(widthPixels: Int, heightPixels: Int) {
            this.widthPixels = widthPixels
            this.heightPixels = heightPixels
        }

        override fun toString(): String {
            return "($widthPixels,$heightPixels)"
        }
    }
}
