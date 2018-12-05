package com.otitan.util

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.otitan.base.BaseAdapter

/**
 * Created by sp on 2018/6/7.
 * 进度弹窗
 */
class MaterialDialogUtil {

    companion object {
        /**
         * 操作信息提示弹窗
         */
        @JvmStatic
        fun showSureDialog(context: Context, msg: String, callback: MaterialDialog.SingleButtonCallback): MaterialDialog {
            return MaterialDialog.Builder(context)
                    .title("提示")
                    .positiveText("确定")
                    .negativeText("取消")
                    .content(msg)
                    .onNegative { dialog, _ ->
                        dialog.dismiss()
                    }
                    .onPositive(callback)
                    .build()
        }

        /**
         * 加载数据进度弹窗
         */
        @JvmStatic
        fun showLoadProgress(context: Context, msg: String, iscancel: Boolean): Dialog {
            return MaterialDialog.Builder(context)
                    .content(msg)
                    .progress(true, 0)
                    .cancelable(iscancel)
                    .canceledOnTouchOutside(iscancel)
                    .build()
        }

        @JvmStatic
        fun showLoadProgress(context: Context, msg: String): MaterialDialog {
            return MaterialDialog.Builder(context)
                    .content(msg)
                    .progress(true, 0)
                    .cancelable(true)
                    .canceledOnTouchOutside(false)
                    .build()
        }

        /**
         * 提示信息dialog
         */
        @JvmStatic
        fun showPromptDialog(context: Context, msg: String): MaterialDialog.Builder {
            return MaterialDialog.Builder(context)
                    .positiveText("确定")
                    .content(msg)
                    .onNegative { dialog, _ ->
                        dialog.dismiss()
                    }
        }

        @JvmStatic
        fun showSingleSelectionDialog(context: Context, title: String, list: List<Any>, callback: MaterialDialog.ListCallback): MaterialDialog {
            return MaterialDialog.Builder(context).title(title).negativeText("取消")
                    .onNegative { dialog, which -> dialog.dismiss() }
                    .items(list)
                    .itemsCallback(callback)
                    .build()
        }

        @JvmStatic
        fun showItemDetailsDialog(context: Context, title: String, adapter: BaseAdapter, layoutManager: LinearLayoutManager): MaterialDialog {
            return MaterialDialog.Builder(context)
                    .adapter(adapter, layoutManager)
                    .title(title)
                    .positiveText("确定")
                    .build()
        }
    }
}