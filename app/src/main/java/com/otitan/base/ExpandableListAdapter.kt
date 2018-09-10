package com.otitan.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.otitan.zjly.BR

/**
 * Created by hanyw on 2018/2/6
 */
abstract class ExpandableListAdapter:BaseExpandableListAdapter() {


    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val groupId=getGroupLayoutIdForPosition(p0)
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(p3!!.context),groupId,p3,false)
        val viewmodel=getGroupViewModel(p0)
        binding.setVariable(BR.viewmodel,viewmodel)
        return binding.root
    }


    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val childId=getChildLayoutIdForPosition(p1)
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(p4!!.context),childId,p4,false)
        val viewmodel=getChildViewModel(p0,p1)
        binding.setVariable(BR.viewmodel,viewmodel)
        return binding.root
    }


    protected abstract fun getGroupLayoutIdForPosition(position: Int): Int

    protected abstract fun getChildLayoutIdForPosition(position: Int): Int

    protected abstract fun getGroupViewModel(position: Int): Any

    protected abstract fun getChildViewModel(position: Int,p1:Int): Any

}