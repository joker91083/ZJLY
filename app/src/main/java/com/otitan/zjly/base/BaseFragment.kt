package com.otitan.zjly.base

import android.app.Dialog
import android.support.v4.app.Fragment
import android.widget.Toast
import com.otitan.zjly.util.MaterialDialogUtil

/**
 * Created by sp on 2018/6/7.
 * Fragment基类
 */
open class BaseFragment : Fragment(), Base {

    //进度弹框
    var mProgressDialog: Dialog? = null

    override fun showToast(type: Int, msg: String?) {
        activity?.let {
            Toast.makeText(it, msg, type).show()
        }
    }

    override fun showProgressDialog(title: String, iscancel: Boolean) {
        mProgressDialog = activity?.let {
            MaterialDialogUtil.showLoadProgress(it, title, iscancel)
        }
        if (mProgressDialog != null && !mProgressDialog?.isShowing!!) {
            mProgressDialog?.show()
        }
    }

    override fun closeProgressDialog() {
        if (this.isAdded) {
            activity?.let {
                mProgressDialog?.dismiss()
            }
        }
    }
}