package com.otitan.base

import android.databinding.BaseObservable
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by hanyw on 2018/1/26
 */
abstract class BaseAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),viewType,parent,false)
        return BaseViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val viewmodel=getLayoutViewModel(position)
        holder.bind(viewmodel as BaseObservable)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    protected abstract fun getLayoutViewModel(position: Int): Any


}