package com.otitan

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.tencent.bugly.Bugly
import com.titan.baselibrary.util.ScreenTool
import kotlin.properties.Delegates

class TitanApplication : Application() {

    var screen: ScreenTool.Screen? = null

    companion object {
        var instances: TitanApplication by Delegates.notNull()
        var handler:Handler? = null

        private val activityList = ArrayList<AppCompatActivity>()

        var sharedPreferences: SharedPreferences by Delegates.notNull()

        fun addActivity(activity: AppCompatActivity) {
            activityList.add(activity)
        }

        fun removeActivity(activity: AppCompatActivity) {
            activityList.remove(activity)
        }

    }

    override fun onCreate() {
        super.onCreate()
        instances = this
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false)
        //true表示打开debug模式，false表示关闭调试模式
        Bugly.init(applicationContext, "f1557e8d0b", true)
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)

        handler = Handler()

        /* 获取屏幕分辨率 */
        screen = ScreenTool.getScreenPix(this)
    }



}