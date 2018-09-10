package com.otitan.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import com.otitan.zjly.R
import kotlin.properties.Delegates

/**
 * Created by hanyw on 2018/8/15
 * 自定义分割线
 */

class TitanItemDecoration(context: Context, orientation: Int,leftRight: Int) : RecyclerView.ItemDecoration() {
    private var mDivider: Drawable by Delegates.notNull()
    private var mOrientation: Int by Delegates.notNull()
    private var leftRight: Int by Delegates.notNull()

    companion object {
        private const val HORIZONTAL_LIST: Int = LinearLayoutManager.HORIZONTAL
        private const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
        //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
        val ATRRS = intArrayOf(android.R.attr.listDivider)
    }

    init {
        this.leftRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,leftRight.toFloat(), context.resources.displayMetrics).toInt()
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider)!!
        setOrientation(orientation)
    }

    //设置屏幕的方向
    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
        if (mOrientation == HORIZONTAL_LIST) {
            drawVerticalLine(c, parent)
        } else {
            drawHorizontalLine(c, parent)
        }
    }

    //画竖线, 这里的parent其实是显示在屏幕显示的这部分
    private fun drawVerticalLine(c: Canvas?, parent: RecyclerView?) {
        if (parent == null) {
            return
        }
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            //获得child的布局信息
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider.intrinsicWidth
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    //画横线
    private fun drawHorizontalLine(c: Canvas?, parent: RecyclerView?) {
        if (parent == null) {
            return
        }
        val left = parent.paddingLeft + leftRight
        val right = parent.width - parent.paddingRight - leftRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            //获得child的布局信息
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    /**
     *
     * @param outRect 边界
     * @param view recyclerView ItemView
     * @param parent recyclerView
     * @param state recycler 内部数据管理
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        //设定底部边距为1px
        if (mOrientation != HORIZONTAL_LIST) {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDivider.intrinsicWidth)
        } else {
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDivider.intrinsicWidth, 0)
        }
    }

}
