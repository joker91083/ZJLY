package com.otitan.main.adapter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.*
import com.otitan.model.*
import com.otitan.util.GsonUtil
import com.otitan.zjly.R

/**
 * 数据管理列表公共适配器
 */
class DataManageAdapter() : BaseAdapter() {
    private var context: Context? = null
    private var items: List<Any>? = null
    private var type: Int? = 1
    private var title = ""

    constructor(context: Context?, items: List<Any>?, title: String) : this() {
        this.context = context
        this.items = items
        this.title = title
    }

    fun setData(items: List<Any>?, type: Int?) {
        this.items = items
        this.type = type
        notifyDataSetChanged()
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        when (title) {
            "森林防火" -> return R.layout.item_slfh_data
            "行政执法" -> return R.layout.item_xzzf_data
            "林地征占" -> return R.layout.item_ldzz_data
            "营造林" -> return R.layout.item_yzl_data
            "有害生物" -> return R.layout.item_yhsw_data
            "国有林场" -> return R.layout.item_gylc_data
            "森林公园" -> return R.layout.item_slgy_data
            "湿地保护" -> return R.layout.item_sdbh_data
        }
        return R.layout.item_lykj
    }

    override fun getLayoutViewModel(position: Int): Any {
        when (title) {
            "森林防火" -> {
                val viewmodel = SlfhDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as SlfhModel.Sjgl?)
                viewmodel.type.set(type ?: 1)
                return viewmodel
            }
            "行政执法" -> {
                val viewmodel = XzzfDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as XzzfModel.Sjgl?)
                return viewmodel
            }
            "林地征占" -> {
                val viewmodel = LdzzDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as LdzzModel.Sjgl?)
                return viewmodel
            }
            "营造林" -> {
                val viewmodel = YzlDataItemViewModel()
                viewmodel.type.set(type ?: 0)
                val obj = getObj(items!![position])
                if (type == 1) {
                    viewmodel.wcqk.set(obj as YzlModel.Wcqk?)
                } else if (type == 2) {
                    viewmodel.jhrw.set(obj as YzlModel.Jhrw?)
                }
                return viewmodel
            }
            "有害生物" -> {
                val viewmodel = YhswDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as YhswModel.Sjgl?)
                return viewmodel
            }
            "国有林场" -> {
                val viewmodel = GylcDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as GylcModel.Sjgl?)
                return viewmodel
            }
            "森林公园" -> {
                val viewmodel = SlgyDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as SlgyModel.Sjgl?)
                return viewmodel
            }
            "湿地保护" -> {
                val viewmodel = SdbhDataItemViewModel()
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as SdbhModel.Sjgl?)
                return viewmodel
            }
            else -> {
                val viewmodel = LykjItemViewModel()
                viewmodel.xmmc.set((items!![position] as LinkedTreeMap<String, Any>)["Name"].toString())
                viewmodel.xmbh.set((items!![position] as LinkedTreeMap<String, Any>)["No"].toString())
                viewmodel.zcr.set((items!![position] as LinkedTreeMap<String, Any>)["HostUser"].toString())
                viewmodel.cddw.set((items!![position] as LinkedTreeMap<String, Any>)["Unit"].toString())
                viewmodel.type.set(this.type ?: 1)
                return viewmodel
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items == null || items!!.isEmpty()) 0 else items!!.size
    }

    fun getObj(any: Any): Any {
        val gson = GsonUtil.getIntGson()
//        val gson = Gson()
        val typeToken = when (title) {
            "森林防火" -> object : TypeToken<SlfhModel.Sjgl>() {}.type
            "行政执法" -> object : TypeToken<XzzfModel.Sjgl>() {}.type
            "林地征占" -> object : TypeToken<LdzzModel.Sjgl>() {}.type
            "营造林" -> {
                if (type == 1) {
                    object : TypeToken<YzlModel.Wcqk>() {}.type
                } else {
                    object : TypeToken<YzlModel.Jhrw>() {}.type
                }
            }
            "有害生物" -> object : TypeToken<YhswModel.Sjgl>() {}.type
            "国有林场" -> object : TypeToken<GylcModel.Sjgl>() {}.type
            "森林公园" -> object : TypeToken<SlgyModel.Sjgl>() {}.type
            "湿地保护" -> object : TypeToken<SdbhModel.Sjgl>() {}.type
            else -> object : TypeToken<SlfhModel.Sjgl>() {}.type
        }
        val json = gson.toJson(any)
        return gson.fromJson(json, typeToken)
    }
}