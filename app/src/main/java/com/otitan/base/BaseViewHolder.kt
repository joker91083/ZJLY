package com.otitan.base

import android.databinding.BaseObservable
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.otitan.zjly.BR


/**
 * Created by whs on 2018/1/26
 */


class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

//    fun bind(item: Any) {
//        binding.setVariable(BR.item, item)
//        binding.executePendingBindings()
//    }

//    fun bind(item: Any, viewModel: BaseObservable) {
//        binding.setVariable(BR.item, item)
//        binding.setVariable(BR.viewmodel, viewModel)
//    }

    fun bind(viewModel: BaseObservable) {
        binding.setVariable(BR.viewmodel, viewModel)
    }
}
