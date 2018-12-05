package com.otitan.main.adapter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.otitan.base.BaseAdapter
import com.otitan.main.viewmodel.*
import com.otitan.model.*
import com.otitan.ui.mview.IEventList
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
    private var mView: IEventList? = null

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

    fun setEventLisstener(mView: IEventList) {
        this.mView = mView
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
            "林业产业" -> return R.layout.item_lycy_data
            "林权" -> return R.layout.item_lquan_data
            "植物检疫" -> return R.layout.item_zwjy_data
            "采伐运输" -> return R.layout.item_cfys_data
            "事件列表" -> return R.layout.item_event_list
        }
        return R.layout.item_lykj
    }

    override fun getLayoutViewModel(position: Int): Any {
        when (title) {
            "森林防火" -> {
                val viewmodel = SlfhDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as SlfhModel.Sjgl?)
                viewmodel.type.set(type ?: 1)
                return viewmodel
            }
            "行政执法" -> {
                val viewmodel = XzzfDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as XzzfModel.Sjgl?)
                return viewmodel
            }
            "林地征占" -> {
                val viewmodel = LdzzDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as LdzzModel.Sjgl?)
                return viewmodel
            }
            "营造林" -> {
                val viewmodel = YzlDataItemViewModel(context)
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
                val viewmodel = YhswDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as YhswModel.Sjgl?)
                return viewmodel
            }
            "国有林场" -> {
                val viewmodel = GylcDataItemViewModel(context)
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
                val viewmodel = SdbhDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as SdbhModel.Sjgl?)
                return viewmodel
            }
            "林权" -> {
                val viewmodel = LQuanDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.type.set(type ?: 0)
                when (type) {
                    1 -> viewmodel.lqzb.set(sjgl as LQuanModel.Lqzb?)
                    else -> viewmodel.djb.set(sjgl as LQuanModel.Djb?)
                }
                return viewmodel
            }
            "植物检疫" -> {
                val viewmodel = ZwjyDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.type.set(type ?: 0)
                viewmodel.sjgl.set(sjgl as ZwjyModel.Sjgl?)
                return viewmodel
            }
            "采伐运输" -> {
                val viewmodel = CfysDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.type.set(type ?: 0)
                when (type) {
                    1 -> viewmodel.cfz.set(sjgl as CfysModel.Cfz?)
                    else -> viewmodel.ysz.set(sjgl as CfysModel.Ysz?)
                }
                return viewmodel
            }
            "林业产业" -> {
                val viewmodel = LycyDataItemViewModel(context)
                val sjgl = getObj(items!![position])
                viewmodel.sjgl.set(sjgl as LycyModel.Sjgl?)
                return viewmodel
            }
            "事件列表" -> {
                val viewmodel = EventListItemViewModel(context, mView)
                val event = getObj(items!![position])
                event as EventModel.EventResult
                when (event.SJLX) {
                    "0" -> event.SJLX = "森林火灾"
                    "1" -> event.SJLX = "森林病虫害"
                    "2" -> event.SJLX = "偷拉盗运"
                    "3" -> event.SJLX = "乱砍滥伐"
                    "4" -> event.SJLX = "征占用林地"
                    "5" -> event.SJLX = "捕杀野生动物"
                }
                if (!event.CJTIME.isNullOrBlank()) {
                    event.CJTIME = event.CJTIME!!.replace("T", " ")
                }
                viewmodel.event.set(event)
                viewmodel.type.set(type ?: 0)
                return viewmodel
            }
            else -> {
                val viewmodel = LykjItemViewModel(context)
                val sjgl = getObj(items!![position])
                when (type) {
                    1 -> viewmodel.syhzxm.set(sjgl as LykjModel.Syhzxm)
                    2 -> viewmodel.sfzjxm.set(sjgl as LykjModel.Sfzjxm)
                    3 -> viewmodel.gjbz.set(sjgl as LykjModel.Gjbz)
                    4 -> viewmodel.hybz.set(sjgl as LykjModel.Hybz)
                    5 -> viewmodel.sjdfbz.set(sjgl as LykjModel.Sjdfbz)
                    6 -> viewmodel.zjzzbz.set(sjgl as LykjModel.Zjzzbz)
                }
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
            "林业科技" -> {
                when (type) {
                    1 -> object : TypeToken<LykjModel.Syhzxm>() {}.type
                    2 -> object : TypeToken<LykjModel.Sfzjxm>() {}.type
                    3 -> object : TypeToken<LykjModel.Gjbz>() {}.type
                    4 -> object : TypeToken<LykjModel.Hybz>() {}.type
                    5 -> object : TypeToken<LykjModel.Sjdfbz>() {}.type
                    else -> object : TypeToken<LykjModel.Zjzzbz>() {}.type
                }
            }
            "林权" -> {
                when (type) {
                    1 -> object : TypeToken<LQuanModel.Lqzb>() {}.type
                    else -> object : TypeToken<LQuanModel.Djb>() {}.type
                }
            }
            "林业产业" -> object : TypeToken<LycyModel.Sjgl>() {}.type
            "植物检疫" -> object : TypeToken<ZwjyModel.Sjgl>() {}.type
            "采伐运输" -> {
                when (type) {
                    1 -> object : TypeToken<CfysModel.Cfz>() {}.type
                    else -> object : TypeToken<CfysModel.Ysz>() {}.type
                }
            }
            "事件列表" -> object : TypeToken<EventModel.EventResult>() {}.type
            else -> object : TypeToken<Any>() {}.type
        }
        val json = gson.toJson(any)
        return gson.fromJson(json, typeToken)
    }
}