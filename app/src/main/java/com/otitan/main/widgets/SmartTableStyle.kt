package com.otitan.main.widgets

import android.content.Context
import android.graphics.Color
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.format.bg.BaseBackgroundFormat
import com.bin.david.form.data.style.FontStyle
import com.bin.david.form.data.style.LineStyle
import com.otitan.util.ScreenTool

class SmartTableStyle{
    companion object {
        @JvmStatic
        fun setTableStyle(table: SmartTable<Any>,context: Context?) {
            //表格设置 隐藏左边数字、顶上字母
            table.config.isShowXSequence = false
            table.config.isShowYSequence = false
            table.config.minTableWidth = ScreenTool.getScreenWidth(context, 26)
            val titleStyle = FontStyle()
            titleStyle.textColor = Color.BLACK
            titleStyle.setTextSpSize(context, 14)
            table.config.tableTitleStyle = titleStyle
            val columnTitleStyle = FontStyle()
            columnTitleStyle.textColor = Color.BLACK
            columnTitleStyle.setTextSpSize(context, 15)
            table.config.columnTitleStyle = columnTitleStyle
            val pad = table.config.verticalPadding
            table.config.verticalPadding = pad + 10
            table.config.columnTitleVerticalPadding = pad + 10
            val bgConfig = BaseBackgroundFormat(Color.rgb(240, 248, 255))
            table.config.columnTitleBackground = bgConfig
            val lineGrid = LineStyle()
            //        lineGrid.setWidth(activity, 1)
            lineGrid.color = Color.BLACK
            table.config.contentGridStyle = lineGrid
            table.config.columnTitleGridStyle = lineGrid
            val contentStyle = FontStyle()
            contentStyle.setTextSpSize(context, 14)
            table.config.contentStyle = contentStyle
        }
    }
}