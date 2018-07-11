package com.otitan.zjly.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.otitan.zjly.util.PadUtil

/**
 * Created by sp on 2018/6/7.
 * activity基类
 */

abstract class BaseActivity : AppCompatActivity(){

    protected var mContext: Context? = null

    /** 日志输出标志 */
    protected val TAG = this.javaClass.simpleName

    /** ViewModel标志 */
    protected val VIEWMODEL_TAG = this.javaClass.simpleName

    protected var mViewModel: BaseViewModel? = null

    protected var mFragment: Fragment? = null

    abstract fun findOrCreateViewFragment(): Fragment
    abstract fun findOrCreateViewModel(): BaseViewModel

    /**
     * 页面跳转
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this@BaseActivity, clz))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        mContext = this
        // 判断是否是pad，是则使用横屏
        requestedOrientation = if (PadUtil.isPad(this)) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    companion object {
        // 矢量支持
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
