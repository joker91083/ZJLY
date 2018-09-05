package com.otitan.base

import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import kotlin.properties.Delegates

open class BaseViewModel() : BaseObservable(), IBaseViewModel {
    protected var mContext: Context? = null
    protected var fragment: Fragment? = null

    constructor(mContext: Context?) : this() {
        this.mContext = mContext
    }

    constructor(fragment: Fragment) : this(fragment.context) {
        this.fragment = fragment
    }

    var dialog: MaterialDialog by Delegates.notNull()

    fun showDialog(title: String) {
        mContext?.let {
            dialog = MaterialDialog.Builder(it).progress(true, 0).title(title).build()
            dialog.show()
        }
    }

    fun dismissDialog() {
        try {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("tag", "dismissErr:$e")
        }

    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        mContext?.startActivity(Intent(mContext, clz))
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(mContext, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        mContext?.startActivity(intent)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(canonicalName: String, bundle: Bundle) {
        val intent = Intent(mContext, ContainerActivity::class.java)
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
        intent.putExtra(ContainerActivity.BUNDLE, bundle)
        mContext?.startActivity(intent)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    fun startContainerActivity(canonicalName: String) {
        val intent = Intent(mContext, ContainerActivity::class.java)
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
        mContext?.startActivity(intent)
    }

    override fun onCreate() {

    }

    override fun onDestroy() {
        dismissDialog()
    }

}