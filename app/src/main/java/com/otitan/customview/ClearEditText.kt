package com.otitan.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.EditText
import com.otitan.zjly.R


/**
 * Created by hanyw on 2018/3/29.
 * 自定义带清除功能的ediText
 */
open class ClearEditText : EditText, View.OnFocusChangeListener, TextWatcher {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context)

    private var mClearDrawable: Drawable? = null
    private var hasFocus: Boolean = false


    private fun init() {
        // getCompoundDrawables() Returns drawables for the left(0), top(1), right(2) and bottom(3)
        mClearDrawable = compoundDrawables[2] // 获取drawableRight
        if (mClearDrawable == null) {
            // 如果为空，即没有设置drawableRight，则使用R.drawable.ic_del2这张图片
            mClearDrawable = ContextCompat.getDrawable(context, R.drawable.ic_del2)
        }
        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth, mClearDrawable!!.intrinsicHeight)
        onFocusChangeListener = this

        addTextChangedListener(this)
        // 默认隐藏图标
        setDrawableVisible(false)
    }

    /**
     * 我们无法直接给EditText设置点击事件，只能通过按下的位置来模拟clear点击事件
     * 当我们按下的位置在图标包括图标到控件右边的间距范围内均算有效
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val start = width - totalPaddingRight + paddingRight // 起始位置
                val end = width // 结束位置
                val available = event.x > start && event.x < end
                if (available) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        this.hasFocus = p1
        if (hasFocus && text.isNotEmpty()) {
            setDrawableVisible(true) // 有焦点且有文字时显示图标
        } else {
            setDrawableVisible(false)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (hasFocus) {
            setDrawableVisible(!p0.isNullOrBlank())
        }
    }

    private fun setDrawableVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }
}