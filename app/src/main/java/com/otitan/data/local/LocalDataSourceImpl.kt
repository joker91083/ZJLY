package com.otitan.data.local

import android.content.Context
import android.util.Log
import com.otitan.TitanApplication
import com.otitan.main.model.TrackPoint
import com.otitan.util.Format
import com.otitan.util.ResourcesManager
import jsqlite.Callback
import java.util.*
import kotlin.collections.ArrayList


class LocalDataSourceImpl():LocalDataSource{

    private object Holder {
        val single = LocalDataSourceImpl()
    }

    companion object {
        val instances: LocalDataSourceImpl by lazy { Holder.single }
    }


    override fun addLocalPoint(lon:String,lat:String,sbh:String,state:String) {

        try {
            var context:Context = TitanApplication.instances
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
            val context:Context = TitanApplication.instances
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
            Log.e("tag","查询轨迹异常:$e")
            callback.onFailure("查询轨迹异常:$e")
        }
    }

}