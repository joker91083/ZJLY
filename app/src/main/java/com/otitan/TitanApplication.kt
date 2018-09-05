package com.otitan

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import com.tencent.bugly.Bugly
import kotlin.properties.Delegates

class TitanApplication : Application() {

    companion object {
        var instances: TitanApplication by Delegates.notNull()

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
    }


}