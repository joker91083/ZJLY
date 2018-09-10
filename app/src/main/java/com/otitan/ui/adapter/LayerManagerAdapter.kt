package com.otitan.ui.adapter

import android.app.Activity
import com.otitan.base.ExpandableListAdapter
import com.otitan.ui.mview.ILayerManagerItem
import com.otitan.ui.vm.LayerManagerItemViewModel
import com.otitan.zjly.R
import java.io.File

class LayerManagerAdapter() : ExpandableListAdapter() {
    private var items: List<Map<String, List<File>>>? = null
    private var activity: Activity? = null
    private var mView: ILayerManagerItem? = null
    private var groups: List<File>? = null
    private var checkedMap: HashMap<String, Boolean>? = null

    constructor(activity: Activity, groups: List<File>, items: List<Map<String, List<File>>>,
                mView: ILayerManagerItem, checked: HashMap<String, Boolean>) : this() {
        this.activity = activity
        this.groups = groups
        this.items = items
        this.mView = mView
        this.checkedMap = checked
    }

    override fun getGroupLayoutIdForPosition(position: Int): Int {
        return R.layout.item_tckz_parent
    }

    override fun getChildLayoutIdForPosition(position: Int): Int {
        return R.layout.item_tckz_child
    }

    override fun getGroupViewModel(position: Int): Any {
        val viewmodel = LayerManagerItemViewModel(activity!!, mView!!)
        viewmodel.title.set((getGroup(position) as File).name.split(".")[0])
        return viewmodel
    }

    override fun getChildViewModel(position: Int, p1: Int): Any {
        val viewmodel = LayerManagerItemViewModel(activity!!, mView!!)
        val file = getChild(position, p1) as File
        viewmodel.title.set(file.name.split(".")[0])
        viewmodel.file = file
        viewmodel.checked.set(checkedMap!![file.absolutePath]!!)
        viewmodel.checkedMap = checkedMap
        return viewmodel
    }

    override fun getGroup(p0: Int): Any {
        return groups!![p0]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChildrenCount(p0: Int): Int {
        return items!![p0][groups!![p0].name!!]!!.size
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return items!![p0][groups!![p0].name!!]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getGroupCount(): Int {
        return groups!!.size
    }
}