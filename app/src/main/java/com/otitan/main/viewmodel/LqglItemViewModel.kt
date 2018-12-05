package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.main.fragment.*
import com.otitan.model.LQGLLable
import com.otitan.ui.mview.ILQGLItem
import com.otitan.util.Utils
import com.otitan.util.Utils.Companion.checkPermission
import kotlin.properties.Delegates

class LqglItemViewModel() : BaseViewModel() {

    private var mView: ILQGLItem by Delegates.notNull()
    val lable = ObservableField<LQGLLable>()
    val dataRepository = Injection.provideDataRepository()
    val menu = TitanApplication.loginResult?.menu

    constructor(context: Context?) : this() {
        this.mContext = context
//        this.mView = mView
//        val menu = TitanApplication.loginResult?.menu
//        if (Utils.checkPermission(activity, menu?.APP_LQGL_ZYGL_SJCX)) {
//        }
    }

    fun onClick() {
        when (lable.get()!!.name) {
            "资源管理" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_ZYGL)) {
                    startContainerActivity(ResourceManageFragment::class.java.canonicalName)
                }
            }
            "森林防火" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_SLFH)) {
                    startContainerActivity(SlfhFragment::class.java.canonicalName)
                }
            }
            "行政执法" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_XZZF)) {
                    startContainerActivity(XzzfFragment::class.java.canonicalName)
                }
            }
            "林地征占" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_LDZZ)) {
                    startContainerActivity(LdzzFragment::class.java.canonicalName)
                }
            }
            "营造林" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_YZL)) {
                    startContainerActivity(YzlFragment::class.java.canonicalName)
                }
            }
            "有害生物" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_YHSW)) {
                    startContainerActivity(YhswFragment::class.java.canonicalName)
                }
            }
            "国有林场" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_GYLC)) {
                    startContainerActivity(GylcFragment::class.java.canonicalName)
                }
            }
            "森林公园" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_SLGY)) {
                    startContainerActivity(SlgyFragment::class.java.canonicalName)
                }
            }
            "湿地保护" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_SDBH)) {
                    startContainerActivity(SdbhFragment::class.java.canonicalName)
                }
            }
            "林业科技" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_LYCY)) {
                    startContainerActivity(LykjFragment::class.java.canonicalName)
                }
            }
            "林权" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_LQ)) {
                    startContainerActivity(LquanFragment::class.java.canonicalName)
                }
            }
            "植物检疫" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_ZWJY)) {
                    startContainerActivity(ZwjyFragment::class.java.canonicalName)
                }
            }
            "采伐运输" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_CFYS)) {
                    startContainerActivity(CfysFragment::class.java.canonicalName)
                }
            }
            "林业产业" -> {
                if (checkPermission(mContext, menu?.APP_LQGL_LYCY)) {
                    startContainerActivity(LycyFragment::class.java.canonicalName)
                }
            }
        }
    }

}