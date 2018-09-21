package com.otitan.util

/**
 * Created by hanyw on 2018/3/26.
 */
class MyComponent : android.databinding.DataBindingComponent {
    override fun getMyDataBindingUtil(): MyDataBindingUtil {
        return if (util == null) {
            MyDataBindingUtil()
        } else {
            util!!
        }
    }

    var util: MyDataBindingUtil? = null
}