package com.otitan.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.*
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.NewViewModel
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmNewBinding
import kotlinx.android.synthetic.main.fm_new.*
import android.view.KeyEvent.KEYCODE_BACK
import android.view.WindowManager


class NewFragment() : BaseFragment<FmNewBinding, NewViewModel>() {

    var viewmodel: NewViewModel? = null
    var url: String = ""

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_new
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): NewViewModel {
        if (viewmodel == null) {
            viewmodel = NewViewModel()
        }
        return viewmodel as NewViewModel
    }

    override fun initParam() {
        super.initParam()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        url = arguments?.getString("url") ?: ""
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        super.initData()
        wb_new.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                return false
            }
        }
        val setting = wb_new.settings
        // 让WebView能够执行javaScript
        setting.javaScriptEnabled = true
        // 让JavaScript可以自动打开windows
        setting.javaScriptCanOpenWindowsAutomatically = true
        // 设置缓存
        setting.setAppCacheEnabled(true)
        // 设置缓存模式,一共有四种模式
//        setting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        // 设置缓存路径
//        setting.setAppCachePath("")
        // 支持缩放(适配到当前屏幕)
        setting.setSupportZoom(true)
        setting.builtInZoomControls = true
        // 将图片调整到合适的大小
        setting.useWideViewPort = true
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        // 设置可以被显示的屏幕控制
        setting.displayZoomControls = true
        // 设置默认字体大小
        setting.defaultFontSize = 12

        val userAgent = setting.userAgentString
        wb_new.loadUrl(url)
    }


//    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return if (wb_new.canGoBack()) {
//                wb_new.goBack() //goBack()表示返回WebView的上一页面
//                true
//            } else {
//                onBackPressed()
//                true
//            }
//        }
//        return false
//    }

}