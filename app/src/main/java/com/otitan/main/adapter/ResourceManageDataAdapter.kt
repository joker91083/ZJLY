package com.otitan.main.adapter

import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.ResourceManageDataItemViewModel
import com.otitan.model.ResourceModel
import com.otitan.zjly.R

class ResourceManageDataAdapter() : BaseAdapter() {

    private var items: List<Any>? = null
    private var type = 0

    constructor(items: List<Any>, type: Int) : this() {
        this.items = items
        this.type = type
    }

    fun setData(items: List<Any>, type: Int) {
        this.items = items
        this.type = type
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_resource_data
    }

    override fun getLayoutViewModel(position: Int): Any {
        val viewmodel = ResourceManageDataItemViewModel()
        val item = items?.get(position)
        viewmodel.type.set(type)
        when (type) {
            1 -> {
                item as ResourceModel.LDMJModel
                viewmodel.dq.set(item.Name)
                viewmodel.ldmj.set(item)
            }
            2 -> {
                item as ResourceModel.SLMJModel
                viewmodel.dq.set(item.Name)
                viewmodel.slmj.set(item)
            }
            3 -> {
                item as ResourceModel.GYLMJModel
                viewmodel.dq.set(item.Name)
                viewmodel.gylmj.set(item)
            }
            4 -> {
                item as ResourceModel.SLFGLModel
                viewmodel.dq.set(item.Name)
                viewmodel.slfgl.set(item)
            }
            5 -> {
                item as ResourceModel.HLMXJModel
                viewmodel.dq.set(item.Name)
                viewmodel.hlmxj.set(item)
            }
        }
        return viewmodel
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}