package com.otitan.main.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.ChangePWViewModel
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmChangepwBinding

class ChangePWFragment : BaseFragment<FmChangepwBinding, ChangePWViewModel>() {
    private var viewmodel: ChangePWViewModel? = null
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_changepw
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): ChangePWViewModel {
        if (viewmodel == null) {
            viewmodel = ChangePWViewModel(activity)
        }
        return viewmodel as ChangePWViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        val toolbar = binding.toolbar
        toolbar.title = "修改密码"
        if (activity != null) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { activity!!.finish() }
        }
        textInputLayoutEditText()
    }

    /**
     * 系统自带的密码开关输入框的错误信息处理
     */
    private fun textInputLayoutEditText() {
        binding.editSure.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when {
                    binding.editSure.text.isNullOrBlank() -> binding.tlSure.isErrorEnabled = false
                    binding.editNew.text.toString() != binding.editSure.text.toString() -> {
                        binding.tlSure.isErrorEnabled = true
                        binding.tlSure.error = "前后密码不相同，请检查"
                    }
                    binding.editNew.text.toString() == binding.editSure.text.toString() -> binding.tlSure.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }
}