package com.otitan.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by whs on 2018/2/5
 * 文本格式化
 */
class FormatUtil {
    companion  object {
        private val df2=DecimalFormat("0.00")
        //经纬度格式化
        private val df_loc=DecimalFormat("#.######")
        //日期格式化
        private var simpleDateFormat=SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //年月日
        private var nyrFormat=SimpleDateFormat("yyyy-MM-dd")

        @JvmStatic fun areaFormat(area: Double):String {
            return df2.format(area)
        }

        /**
         * 时间格式化
         */
        @JvmStatic
        fun dateFormat():String{
            return simpleDateFormat.format(Date())
        }

        /**
         * 字符串转日期
         */
        @JvmStatic
        fun dateParse(date:String)= simpleDateFormat.parse(date)

        /**
         * 时间格式化
         */
        @JvmStatic
        fun dateFormat(millseconds: Long):String{
            return simpleDateFormat.format(millseconds)
        }

        /**
         * 时间格式化
         */
        @JvmStatic
        fun dateFormatNYR(millSeconds: Long):String{
            return nyrFormat.format(millSeconds)
        }

        /**
         * 经纬度格式化
         */
        @JvmStatic fun LocFormat(lon: Double):String {
            return df_loc.format(lon)
        }
    }
}