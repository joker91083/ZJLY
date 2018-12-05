package com.otitan.main.utils

import android.content.Context
import com.otitan.model.DataItemModel
import com.otitan.util.GsonUtil

class ItemUtils {
    companion object {
        @JvmStatic
        fun getList(mContext: Context?, obj: Any?, type: String?): List<DataItemModel> {
            val gson = GsonUtil.getIntGson()
            val json = gson.toJson(obj)
            val map = gson.fromJson<HashMap<String, Any>>(json, HashMap::class.java)
            val list = ArrayList<DataItemModel>()
            map.forEach { (s, any) ->
                val o = DataItemModel()
                var key = s
                when (type) {
                    "xzzf", "slfh" -> {
                        if (key == "Remark") {
                            key = "${key}_$type"
                        }
                    }
                    "yhsw" -> {
                        if (key == "Money" || key == "Area") {
                            key = "${key}_$type"
                        }
                    }
                    "sdbh" -> {
                        if (key == "Name") {
                            key = "${key}_$type"
                        }
                    }
                    "lykj", "lykj_1" -> {
                        if (key == "Name" || key == "No" || key == "Unit" || key == "Area") {
                            key = "${key}_$type"
                        }
                    }
                    "lq_2" -> {
                        if (key == "Kind" || key == "VillageName" || key == "Unit") {
                            key = "${key}_$type"
                        }
                    }
                }

                val id = mContext?.resources?.getIdentifier(key, "string", mContext.packageName)
                if (id != null && id != 0) {
                    o.name = mContext.resources?.getString(id).toString()
                    o.value = any.toString().replace(Regex("\\d+T\\d?")) { it.value.replace("T", " ") }
                    list.add(o)
                }
            }
            list.sortBy { it.name }
            return list
        }
    }

}

