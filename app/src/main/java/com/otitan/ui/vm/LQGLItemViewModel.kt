package com.otitan.ui.vm

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.model.LQGLLable
import com.otitan.ui.mview.ILQGL
import com.otitan.ui.mview.ILQGLItem
import kotlin.properties.Delegates

class LQGLItemViewModel():BaseViewModel(){

    private var mView:ILQGLItem by Delegates.notNull()
    val lable = ObservableField<LQGLLable>()

    constructor(context: Context?):this(){
        this.mContext = context
//        this.mView = mView
    }

}