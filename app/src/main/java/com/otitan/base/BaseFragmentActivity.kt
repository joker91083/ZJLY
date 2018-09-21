package com.otitan.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import butterknife.ButterKnife
import com.otitan.TitanApplication

abstract class BaseFragmentActivity : AppCompatActivity(){

    /** 上下文  */
    protected  var mContext: Context? = null
    /** 日志输出标志  */
    val TAG = this.javaClass.simpleName

    abstract fun findOrCreateViewFragment(): Fragment
    abstract fun findOrCreateViewModel(): BaseViewModel

    abstract fun setLayoutId():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(setLayoutId())
        mContext = this
        TitanApplication.addActivity(this)
        ButterKnife.bind(this);
        //判断平板使用横屏,手机使用竖屏
//        requestedOrientation = if (DeviceUtil.isMoreThan6Inch(this)||DeviceUtil.isTablet(this) ) {
//            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE//强制为横屏
//        } else {
//            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
//        }
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
        //矢量支持
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

}
