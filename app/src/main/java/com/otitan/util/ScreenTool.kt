package com.otitan.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * Created by li on 2017/5/31.
 * 设置尺寸类
 */
object ScreenTool {

    fun getScreenPix(context: Context): Screen {
        val dm = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(dm)
        return Screen(dm.widthPixels, dm.heightPixels)
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
