package com.otitan.main.viewmodel

import android.content.Context
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.IZjjc

class ZjjcViewModel() : BaseViewModel() {

    var mView: IZjjc? = null

    constructor(context: Context?, mView: IZjjc) : this() {
        this.mContext = context
        this.mView = mView
    }
}
