package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.os.Bundle
import com.otitan.base.BaseViewModel
import com.otitan.main.fragment.NewFragment
import com.otitan.model.LydtModel

class NewListItemViewModel() : BaseViewModel() {

    val model = ObservableField<LydtModel>()

    constructor(context: Context?) : this() {
        mContext = context
    }

    fun onClick() {
        val bundle = Bundle()
        bundle.putString("url", model.get()?.newsurl)
        startContainerActivity(NewFragment::class.java.canonicalName, bundle)
    }
}