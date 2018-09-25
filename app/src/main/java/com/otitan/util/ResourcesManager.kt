package com.otitan.util

import android.content.Context
import android.database.Observable
import android.os.storage.StorageManager
import java.io.File
import java.io.FileNotFoundException
import java.io.Serializable
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class ResourcesManager : Serializable {


    val ROOT_MAPS = "/maps"
    private val otitan_map = "/otitan.map"
    private val sqlite = "/sqlite"
    val otms = "/otms"
    val filePath = "文件可用地址"

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
        var dataPath = filePath
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

    /** 获取影像文件列表  */
    fun getImgTitlePath(): List<File> {
        val fileter = ArrayList<String>()
        fileter.add("image")
        return getPahts(otitan_map, fileter)
    }

    fun getPahts(path: String, keywords: List<String>): List<File> {
        val list = ArrayList<File>()
        val array = getMemoryPath()
        for (i in array!!.indices) {
            val file = File(array[i] + ROOT_MAPS + path)
            if (file.exists()) {
                for (m in 0 until file.listFiles().size) {
                    if (file.listFiles()[m].isFile && (file.listFiles()[m].name.contains(keywords[0]))) {
                        list.add(file.listFiles()[m])
                    }
                }
            }
        }
        return list
    }

    /** 获取otms文件夹下的文件夹  */
    fun getOtmsFolder(): List<File> {
        val path = otms
        val files = File(getFolderPath(path)).listFiles()
        if(files == null){
            return ArrayList<File>()
        }
        val groups = ArrayList<File>()
        val filesLength = files.size
        for (i in 0 until filesLength) {
            if (!files[i].isDirectory) {
                continue
            }
            groups.add(files[i])
        }
        return groups
    }

    /** 获取文件夹可用地址  */
    fun getFolderPath(path: String): String {
        var dataPath = filePath
        val memoryPath = getMemoryPath()
        for (i in memoryPath!!.indices) {
            val file = File(memoryPath[i] + ROOT_MAPS + path)
            if (file.exists()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path
                break
            } else {
                if (path == "") {
                    file.mkdirs()
                }
            }
        }
        return dataPath
    }

    /** 获取otms中每个文件夹下的.otms或者.geodatabase数据  */
    fun getChildData(groups: List<File>): List<Map<String, List<File>>> {
        val childs = ArrayList<Map<String, List<File>>>()
        for (i in 0 until groups.size) {
            val path = otms + "/" + groups[i].name
            val files = File(getFolderPath(path)).listFiles()
            val map = HashMap<String, List<File>>()
            map[groups[i].name] = getOtmsData(files)
            childs.add(map)
        }
        return childs
    }

    fun getOtmsData(files: Array<File>): ArrayList<File> {
        val list = ArrayList<File>()
        if (files.isNotEmpty()) {
            for (file in files) {
                if (file.isDirectory)
                    continue
                if (file.name.endsWith(".otms") || file.name.endsWith(".geodatabase")) {
                    list.add(file)
                }
            }
        }
        return list
    }


    fun getDataBase(dbname:String):String{
        if (dbname.equals(""))
            return ""
        var db: File? = null
        db = File(getFilePath(sqlite + "/" + dbname))
        if (db.exists()) {
            return db.toString()
        }
        throw FileNotFoundException(Constant.fileNotFound)
    }

    fun getOtms(key:String) :ArrayList<File>{
        var arrays :ArrayList<File> = ArrayList<File>()
        var folderpath = getFolderPath(otms)
        var list: Array<out File>? = File(folderpath).listFiles()
        for (i in list!!.indices){
            var flie = list.get(i)
            if(flie.name.contains(key)){
                arrays.add(flie)
            }
        }

        return arrays
    }

    fun getOtmsChild(list:ArrayList<File>):ArrayList<HashMap<String,ArrayList<File>>>{
        var arrayList:ArrayList<HashMap<String,ArrayList<File>>> = ArrayList<HashMap<String,ArrayList<File>>>()
        for(i in list!!.indices){
            var map:HashMap<String,ArrayList<File>> = HashMap<String,ArrayList<File>>()
            var file:File = list.get(i)

            var path = file.path
            var array: Array<out File>?=File(path).listFiles()

            var fileList :ArrayList<File> = ArrayList<File>()
            for(j in array!!.indices){
                var ff:File = array.get(j)
                if(ff.name.endsWith(".getodabase") || ff.name.endsWith(".shp")){
                    fileList.add(ff)
                }
            }
            map.put(file.name,fileList)
            arrayList.add(map)
        }
        return arrayList
    }



}