package com.otitan.ui.vm

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.IMain
import kotlin.properties.Delegates

class MainViewModel():BaseViewModel(){

    private var mView:IMain by Delegates.notNull()

    constructor(context: Context,mView:IMain):this(){
        this.mContext = context
        this.mView = mView
    }
}