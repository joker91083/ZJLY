package com.otitan

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.iflytek.cloud.SpeechUtility
import com.otitan.data.DataRepository
import com.otitan.data.Injection
import com.otitan.data.remote.RemoteDataSource
import com.otitan.main.model.RepealInfo
import com.otitan.model.LoginResult
import com.otitan.model.MyObjectBox
import com.otitan.util.ResourcesManager
import com.otitan.util.ToastUtil
import com.otitan.util.ZIPUtil
import com.otitan.zjly.R
import com.tencent.bugly.Bugly
import com.titan.baselibrary.util.MobileInfoUtil
import com.titan.baselibrary.util.ScreenTool
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.android.BuildConfig
import org.jetbrains.anko.async
import java.io.*
import kotlin.properties.Delegates

class TitanApplication : Application() {

    var screen: ScreenTool.Screen? = null
    var sbh: String = ""
    var dataRepository: DataRepository? = null

    companion object {
        var instances: TitanApplication by Delegates.notNull()
        var handler: Handler? = null
        val repealInfoList = ArrayList<RepealInfo>()
        var loginResult: LoginResult? = null

        //本地数据库
        var boxStore: BoxStore? = null

        private val activityList = ArrayList<AppCompatActivity>()

        var sharedPreferences: SharedPreferences by Delegates.notNull()

        fun addActivity(activity: AppCompatActivity) {
            activityList.add(activity)
        }

        fun removeActivity(activity: AppCompatActivity) {
            activityList.remove(activity)
        }

    }

    override fun onCreate() {
        super.onCreate()
        instances = this
//        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false)
        //true表示打开debug模式，false表示关闭调试模式
        Bugly.init(applicationContext, "f1557e8d0b", true)
        SpeechUtility.createUtility(applicationContext, "appid=" + getString(R.string.app_id))
        sharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
        //ObjectBox初始化
        boxStore = MyObjectBox.builder().androidContext(this.applicationContext).build()
        if (BuildConfig.DEBUG) {
            //打开调试信息
            AndroidObjectBrowser(boxStore).start(this)
        }
        dataRepository = Injection.provideDataRepository()
        handler = Handler()

        /* 获取屏幕分辨率 */
        screen = ScreenTool.getScreenPix(this)

        sbh = MobileInfoUtil.getMAC(this)

        async {
            val path = ResourcesManager.getInstances(applicationContext).getTootPath()
            copyDatabase(path, "maps.zip", "maps.zip")
        }
    }

    /**
     * 设备信息注册
     */
    fun registerMobile() {
//        Log.e("tag", "设备注册：${sharedPreferences.getBoolean("registerMobile", false)},sbh:$sbh")
//        if (sharedPreferences.getBoolean("registerMobile", false)) {
//            return
//        }
        if (loginResult?.access_token.isNullOrBlank()) {
            return
        }
        dataRepository?.registerMobile("Bearer " + loginResult?.access_token!!, sbh, object : RemoteDataSource.mCallback {
            override fun onFailure(info: String) {
                ToastUtil.setToast(instances, "设备注册失败：$info")
                Log.e("tag", "设备注册失败：$info")
                sharedPreferences.edit().putBoolean("registerMobile", false).apply()
            }

            override fun onSuccess(result: Any) {
                Log.e("tag", "registerMobile:$result")
                sharedPreferences.edit().putBoolean("registerMobile", result as Boolean).apply()
            }
        })
    }

    /** 复制默认数据到本地  */
    private fun copyDatabase(fileDir: String, assetPath: String, filename: String) {
        val file = File("$fileDir/$filename")
        if (file.exists() && file.isFile) {
            UnZipTFolder(fileDir, filename)
            return
        }
        try {
            val db = resources.assets.open(assetPath) ?: return
            val fos = FileOutputStream("$fileDir/$filename")

            var read:Int = -1
            db.use { input->
                fos.use {
                    while ({read = input.read();read}()!=-1){
                        it.write(read)
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        UnZipTFolder(fileDir, filename)
    }


    /**解压文件并删除zip文件 */
    fun UnZipTFolder(fileDir: String, filename: String) {
        try {
            //ZIPUtil.UnZipFolder(fileDir+"/"+filename, fileDir+"/");
            ZIPUtil.unzip("$fileDir/$filename", "$fileDir/")
        } catch (e: Exception) {//java.io.FileNotFoundException: /storage/emulated/0/maps.zip: open failed: ENOENT (No such file or directory)
            e.printStackTrace()
        }

        //删除本地的zip压缩文件
        File("$fileDir/$filename").delete()
    }

    inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
        var exception: Throwable? = null
        try {
            return block(this)
        } catch (e: Throwable) {
            exception = e
            throw  e
        } finally {
            when {
                this == null -> {
                }
                exception == null -> close()
                else ->
                    try {
                        close()
                    } catch (closeException: Throwable) {
                        Log.e("tag", "closeErr:$closeException")
                    }
            }
        }
    }
}