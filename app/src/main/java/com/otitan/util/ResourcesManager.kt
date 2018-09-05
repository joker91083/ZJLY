package com.otitan.util

import android.content.Context
import android.os.storage.StorageManager
import java.io.File
import java.io.Serializable
import java.lang.reflect.InvocationTargetException
import kotlin.properties.Delegates

class ResourcesManager : Serializable {


    val ROOT_MAPS = "/maps"
    private val otitan_map = "/otitan.map"

    companion object {
        private var mContext: Context by Delegates.notNull()
        private var manager: ResourcesManager? = null
        fun getInstances(context: Context): ResourcesManager {
            mContext = context
            if (manager == null) {
                manager = ResourcesManager()
            }
            return manager!!
        }
    }

    /**
     *  获取手机内部存储地址和外部存储地址
     */
    fun getMemoryPath(): Array<String>? {
        val sm = mContext.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        var paths: Array<String>? = null
        try {
            //paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[0]).invoke(sm,new Object[]{});
            paths = sm.javaClass.getMethod("getVolumePaths").invoke(sm) as Array<String>
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        return paths
    }

    /** 取文件可用地址  */
    fun getFilePath(path: String): String {
        var dataPath = "文件可用地址"
        val memoryPath = getMemoryPath()
        for (i in memoryPath!!.indices) {
            val file = File(memoryPath[i] + ROOT_MAPS + path)
            if (file.exists() && file.isFile()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path
                break
            }
        }
        return dataPath
    }

    /** 获取基础地图的本地路径  */
    fun getTitlePath(): String {
        val name = "$otitan_map/title.tpk"
        return getFilePath(name)
    }
}