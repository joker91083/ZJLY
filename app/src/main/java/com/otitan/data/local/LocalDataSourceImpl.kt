package com.otitan.data.local

import android.content.Context
import com.otitan.TitanApplication
import com.otitan.util.Format
import com.otitan.util.ResourcesManager



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

}