package com.otitan.ui.vm

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.ILQGL
import kotlin.properties.Delegates

class LQGLViewModel():BaseViewModel(){

    private var mView:ILQGL by Delegates.notNull()

    constructor(context: Context?,mView:ILQGL):this(){
        this.mContext = context
        this.mView = mView
    }

}