package com.otitan.ui.vm

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.IImgManager
import kotlin.properties.Delegates

class ImgManagerViewModel():BaseViewModel(){
    private var mView: IImgManager by Delegates.notNull()

    constructor(context: Context, mView: IImgManager) : this() {
        mContext = context
        this.mView = mView
    }

    fun close(){
        mView.close()
    }
}