package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.os.Bundle
import com.otitan.TitanApplication
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.main.fragment.*
import com.otitan.model.LQGLLable
import com.otitan.model.ResourceModel
import com.otitan.model.ResultModel
import com.otitan.ui.mview.ILQGL
import com.otitan.ui.mview.ILQGLItem
import com.otitan.util.ToastUtil
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

class LqglItemViewModel() : BaseViewModel() {

    private var mView: ILQGLItem by Delegates.notNull()
    val lable = ObservableField<LQGLLable>()
    val dataRepository = Injection.provideDataRepository()

    constructor(context: Context?) : this() {
        this.mContext = context
//        this.mView = mView
    }

    fun onClick() {
        when (lable.get()!!.name) {
            "资源管理" -> startContainerActivity(ResourceManageFragment::class.java.canonicalName)
            "森林防火" -> startContainerActivity(SlfhFragment::class.java.canonicalName)
            "行政执法" -> startContainerActivity(XzzfFragment::class.java.canonicalName)
            "林地征占" -> startContainerActivity(LdzzFragment::class.java.canonicalName)
            "营造林" -> startContainerActivity(YzlFragment::class.java.canonicalName)
            "有害生物" -> startContainerActivity(YhswFragment::class.java.canonicalName)
            "国有林场" -> startContainerActivity(GylcFragment::class.java.canonicalName)
            "森林公园" -> startContainerActivity(SlgyFragment::class.java.canonicalName)
            "湿地保护" -> startContainerActivity(SdbhFragment::class.java.canonicalName)
            "林业科技" -> startContainerActivity(LykjFragment::class.java.canonicalName)
            "林权" -> startContainerActivity(LquanFragment::class.java.canonicalName)
            "植物检疫" -> startContainerActivity(ZwjyFragment::class.java.canonicalName)
            "采伐运输" -> startContainerActivity(CfysFragment::class.java.canonicalName)
            "林业产业" -> startContainerActivity(LycyFragment::class.java.canonicalName)
        }
    }

}