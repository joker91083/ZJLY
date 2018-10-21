package com.otitan.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.LinearLayout
import com.otitan.TitanApplication
import com.otitan.base.ContainerActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class Utils {
    companion object {
        private val sNextGeneratedId = AtomicInteger(1)
        /**
         * Generate a value suitable for use in [.setId].
         * This value will not collide with ID values generated at build time by aapt for R.id.
         *
         * @return a generated ID value
         */
        fun generateViewId(): Int {
            while (true) {
                val result = sNextGeneratedId.get()
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                var newValue = result + 1
                if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result
                }
            }
        }

        /**
         * 获取当天的0点时间戳
         */
        fun getZeroTime(): Long {
            val current = System.currentTimeMillis();//当前时间毫秒数
            return current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().rawOffset
        }

        fun dp2px(value: Float, context: Context): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, context.resources.displayMetrics)
        }

        /**动态设置控件的高度 */
        fun setExpendHeight(adapter: BaseExpandableListAdapter, listView: ExpandableListView) {
            var listViewHeight = 0
            val adaptCount = adapter.groupCount
            for (i in 0 until adaptCount) {
                val temp = adapter.getGroupView(i, true, null, listView)
                temp.measure(0, 0)
                listViewHeight += temp.measuredHeight
            }
            val layoutParams = listView.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = LinearLayout.LayoutParams.FILL_PARENT
            val screen = ScreenTool.getScreenPix(TitanApplication.instances)
            if (listViewHeight > screen.heightPixels / 2) {
                layoutParams.height = screen.heightPixels / 2 - 60
            } else {
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            }
            listView.layoutParams = layoutParams
        }

        /**
         * 跳转容器页面
         *
         * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
         * @param bundle        跳转所携带的信息
         */
        @JvmStatic
        fun startContainerActivity(context: Context?,canonicalName: String, bundle: Bundle) {
            val intent = Intent(context, ContainerActivity::class.java)
            intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
            intent.putExtra(ContainerActivity.BUNDLE, bundle)
            context?.startActivity(intent)
        }

        /**
         *
         */
        @JvmStatic
        fun getProperties(context: Context, proName: String): String {
            var pro_value: String?
            val props = Properties()
            try {
                val inputStream = context.assets.open("config")
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                props.load(bufferedReader)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            pro_value = props.getProperty(proName)
            if (pro_value == null) {
                pro_value = ""
            }
            return pro_value
        }
    }
}