package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.LQGLLable
import com.otitan.ui.mview.ILQGL
import com.otitan.ui.mview.ILQGLItem
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

class LqglItemViewModel():BaseViewModel(){

    private var mView:ILQGLItem by Delegates.notNull()
    val lable = ObservableField<LQGLLable>()

    constructor(context: Context?):this(){
        this.mContext = context
//        this.mView = mView
    }

    fun onClick(){
        mContext?.toast(lable.get().name)
    }

}