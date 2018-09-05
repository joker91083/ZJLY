package com.otitan.util

import android.content.Context
import android.util.TypedValue
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
    }
}