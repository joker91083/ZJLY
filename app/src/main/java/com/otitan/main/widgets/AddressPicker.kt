package com.otitan.main.widgets

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.otitan.model.XZQHModel
import com.otitan.util.Utils
import com.otitan.zjly.R

/**
 * 浙江省行政区划选择器
 */
class AddressPicker<T>() {
    private var thread: Thread? = null
    var isLoaded = false
    private var handler: Handler? = null
    var context: Context? = null
    var picker: OptionsPickerView<T>? = null
    //地址选择器数据
    val options1Items = ArrayList<XZQHModel>()//省
    val options2Items = ArrayList<ArrayList<XZQHModel.CityModel>>()//市
    val options3Items = ArrayList<ArrayList<ArrayList<XZQHModel.CountyModel>>>()//县

    constructor(context: Context,titleText: String, listener: OnOptionsSelectListener) : this() {
        this.context = context
        initHandler()
        conversion()
        optionsPickerBuild(context, titleText, listener)
    }

    private fun optionsPickerBuild(context: Context, titleText: String, listener: OnOptionsSelectListener) {
        picker = OptionsPickerBuilder(context, listener).setTitleText(titleText)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                .setCancelColor(Color.WHITE)//取消按钮文字颜色
                .setTitleBgColor(0xFF4CAF50.toInt())//标题背景颜色 Night mode
//                .setBackgroundId(R.drawable.background_view_rounded_image)
                .setContentTextSize(18)
                .build<T>()
    }

    private fun initHandler() {
        if (handler == null) {
            handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message?) {
                    when (msg?.what) {
                        1 -> {
                            picker?.setPicker(options1Items as List<T>, options2Items as List<MutableList<T>>,
                                    options3Items as List<MutableList<MutableList<T>>>)
                            isLoaded = true
                        }
                    }
                }
            }
        }
    }

    private fun conversion() {
        context?.let {
            if (thread == null) {
                thread = Thread(Runnable {
                    val json = Utils.getXZQHJson("zhejiang.json", it)
                    val type = object : TypeToken<XZQHModel>() {}.type
                    val model = Gson().fromJson<XZQHModel>(json, type)
                    options1Items.add(model)
                    val cityList = ArrayList<XZQHModel.CityModel>()
                    val countyList = ArrayList<ArrayList<XZQHModel.CountyModel>>()
                    cityList.add(XZQHModel.CityModel())
                    val temp = ArrayList<XZQHModel.CountyModel>()
                    temp.add(XZQHModel.CountyModel())
                    countyList.add(temp)
                    model.ChildAdministrativeDivisions.forEach {
                        cityList.add(it)
                        val countys = ArrayList<XZQHModel.CountyModel>()
                        countys.add(XZQHModel.CountyModel())
                        it.ChildAdministrativeDivisions.forEach {
                            countys.add(it)
                        }
                        countyList.add(countys)
                    }
                    options2Items.add(cityList)
                    options3Items.add(countyList)
                    handler?.sendEmptyMessage(1)
                })
            }
            thread?.start()
        }
    }

}