package com.otitan.ui.adapter

import android.app.Activity
import com.otitan.base.BaseAdapter
import com.otitan.model.LQGLLable
import com.otitan.ui.mview.ILQGLItem
import com.otitan.ui.vm.LQGLItemViewModel
import com.otitan.zjly.R
import kotlin.properties.Delegates

class LQGLAdapter() : BaseAdapter() {
    private var activity: Activity by Delegates.notNull()
    private var mView: ILQGLItem by Delegates.notNull()
    private var items: List<LQGLLable> by Delegates.notNull()

    constructor(activity: Activity, items: List<LQGLLable>) : this() {
        this.activity = activity
//        this.mView = mView
        this.items = items
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_lqgl_label
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = LQGLItemViewModel(activity)
        viewmodel.lable.set(items[position])
        return viewmodel
    }

    override fun getItemCount(): Int {
        return items.size
    }
}