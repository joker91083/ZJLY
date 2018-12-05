package com.otitan.data.local

import android.content.Context
import android.util.Log
import com.otitan.TitanApplication
import com.otitan.main.model.TrackPoint
import com.otitan.model.EventModel
import com.otitan.model.POIModel
import com.otitan.util.Format
import com.otitan.util.ResourcesManager
import jsqlite.Callback
import java.util.*
import kotlin.collections.ArrayList


class LocalDataSourceImpl() : LocalDataSource {


    private object Holder {
        val single = LocalDataSourceImpl()
    }

    companion object {
        val instances: LocalDataSourceImpl by lazy { Holder.single }
        val eventBox = TitanApplication.boxStore?.boxFor(EventModel::class.java)
    }


    override fun addLocalPoint(lon: String, lat: String, sbh: String, state: String) {

        try {
            var context: Context = TitanApplication.instances
            val databaseName = ResourcesManager.getInstances(context).getDataBase("guiji.sqlite")
            Class.forName("jsqlite.JDBCDriver").newInstance()
            val db = jsqlite.Database()
            db.open(databaseName, jsqlite.Constants.SQLITE_OPEN_READWRITE)
            var time = Format.timeFormat()
            val sql = ("insert into point values(null," + lon + "," + lat
                    + ",'" + sbh + "','" + time + "'," + state
                    + ",geomfromtext('POINT(" + lon + " " + lat + ")',2343))")
            db.exec(sql, null)
            db.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun queryTrackPoint(stratime: String, endtime: String, callback: LocalDataSource.Callback) {
        val list = ArrayList<TrackPoint>()
        try {
            val context: Context = TitanApplication.instances
            val sbh = TitanApplication.instances.sbh
            val databaseName = ResourcesManager.getInstances(context).getDataBase("guiji.sqlite")
            Class.forName("jsqlite.JDBCDriver").newInstance()
            val db = jsqlite.Database()
            db.open(databaseName, jsqlite.Constants.SQLITE_OPEN_READWRITE)
            val sql = ("SELECT * FROM point WHERE sbh ='" + sbh
                    + "' and time <= datetime('" + stratime
                    + "') and time >=('" + endtime
                    + "') order by datetime(time) desc")
            db.exec(sql, object : Callback {

                override fun types(arg0: Array<String>) {

                }

                override fun newrow(data: Array<String>): Boolean {// 3 5 6
                    if (data[1] != null && data[2] != null && data[4] != null) {
                        val point = TrackPoint()
                        point.lon = data[1]
                        point.lat = data[2]
                        point.time = data[4]
                        list.add(point)
                    }
                    return false
                }

                override fun columns(arg0: Array<String>) {}
            })
            db.close()
            callback.onSuccess(list)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("tag", "查询轨迹异常:$e")
            callback.onFailure("查询轨迹异常:$e")
        }
    }

    override fun queryPOI(name: String, callback: LocalDataSource.Callback) {
        val list = ArrayList<POIModel>()
        try {
            val context: Context = TitanApplication.instances
            val databaseName = ResourcesManager.getInstances(context).getDataBase("ZJPOI.sqlite")
            Class.forName("jsqlite.JDBCDriver").newInstance()
            val db = jsqlite.Database()
            db.open(databaseName, jsqlite.Constants.SQLITE_OPEN_READWRITE)
            val sql = ("select * from tp_area_0_0 where name like '%$name%'")
            db.exec(sql, object : Callback {
                override fun newrow(array: Array<out String>?): Boolean {
                    val poi = POIModel()
                    if (array != null) {
                        poi.id = array[0]
                        poi.parent_id = array[1]
                        poi.name = array[2]
                        poi.area_code = array[3]
                        poi.city_code = array[4]
                        poi.merger_name = array[5]
                        poi.short_name = array[6]
                        poi.zip_code = array[7]
                        poi.level = array[8]
                        poi.lng = array[9]
                        poi.lat = array[10]
                        poi.pinyin = array[11]
                        poi.first = array[12]
                        list.add(poi)
                    }
                    return false
                }

                override fun columns(p0: Array<out String>?) {

                }

                override fun types(p0: Array<out String>?) {
                }
            })
            db.close()
            callback.onSuccess(list)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("tag", "poi查询异常:$e")
            callback.onFailure("poi查询异常:$e")
        }
    }

    override fun saveEvent(eventModel: EventModel, callback: LocalDataSource.Callback) {
        val id = eventBox?.put(eventModel)
        if (id != null && id != 0L) {
            callback.onSuccess(id)
        } else {
            callback.onFailure("事件保存失败")
        }
    }

    override fun queryEvent(callback: LocalDataSource.Callback) {
        try {
            val list = eventBox?.all
            callback.onSuccess(list)
        } catch (e: Exception) {
            callback.onFailure("查询事件异常$e")
        }
    }

    override fun delEvent(id: Long, callback: LocalDataSource.Callback) {
        try {
            eventBox?.remove(id)
            callback.onSuccess("删除成功")
        } catch (e: Exception) {
            callback.onFailure("删除事件异常$e")
        }
    }
}