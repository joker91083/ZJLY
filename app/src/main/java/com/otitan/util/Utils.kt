package com.otitan.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.util.TypedValue
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.LinearLayout
import com.otitan.TitanApplication
import com.otitan.base.ContainerActivity
import org.jetbrains.anko.toast
import java.io.*
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

        /**
         * 获取当前时间的一年之前的时间
         */
        fun getBeforeYearTime(count: Int): Long {
            val c = Calendar.getInstance()
            c.time = Date()
            c.add(Calendar.YEAR, -count)
            val y = c.time
            return y.time
        }

        /**
         * 获取当前时间的一月之前的时间
         */
        fun getBeforeMonthTime(count: Int): Long {
            val c = Calendar.getInstance()
            c.time = Date()
            c.add(Calendar.MONTH, -count)
            val y = c.time
            return y.time
        }

        /**
         * 获取当前时间的一周之前的时间
         */
        fun getBeforeWeekTime(count: Int): Long {
            val c = Calendar.getInstance()
            c.time = Date()
            c.add(Calendar.DATE, -count * 7)
            val y = c.time
            return y.time
        }

        /**
         * 获取当前时间的一天之前的时间
         */
        fun getBeforeDayTime(count: Int): Long {
            val c = Calendar.getInstance()
            c.time = Date()
            c.add(Calendar.DATE, -count)
            val y = c.time
            return y.time
        }

        fun dp2px(value: Float, context: Context): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
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
        fun startContainerActivity(context: Context?, canonicalName: String, bundle: Bundle) {
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

        //获取浙江行政区划json字符串
        @JvmStatic
        fun getXZQHJson(fileName: String, context: Context): String {
            var inputStream: InputStream? = null
            var bos: ByteArrayOutputStream? = null
            try {
                inputStream = context.assets.open(fileName)//"zhejiang.json"
                bos = ByteArrayOutputStream()
                val bytes = ByteArray(4 * 1024)
                var len: Int
                do {
                    len = inputStream.read(bytes)
                    if (len == -1) {
                        break
                    }
                    bos.write(bytes, 0, len)
                } while (true)
                return String(bos.toByteArray())
            } catch (e: Exception) {
                Log.e("tag", "read zhejiang error:$e")
            } finally {
                try {
                    inputStream?.close()
                    bos?.close()
                } catch (e: IOException) {
                    Log.e("tag", "closeErr:$e")
                }
            }
            return ""
        }

        /**
         * 文字样式添加
         */
        fun getSpanned(s: String): Spanned? {
            var s1 = s
            s1 = s1.replace(Regex("\\d+(\\.\\d+)?")) { "<font color=#FF4081><b>${it.value}</b></font>" }
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(s1, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(s1)
            }
        }


        /**
         * 检测按钮权限
         */
        fun checkPermission(mContext: Context?, code: Boolean?): Boolean {
            if (code == true) {
                return true
            }
            mContext?.toast("不具备当前菜单权限")
            return false
        }

    }
}