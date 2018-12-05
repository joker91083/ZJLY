package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.iflytek.cloud.*
import com.iflytek.cloud.ui.RecognizerDialogListener
import com.iflytek.sunflower.FlowerCollector
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.IVoice
import com.otitan.util.ToastUtil
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import kotlin.properties.Delegates

class VoiceSearchViewModel() : BaseViewModel() {

    //搜索内容
    val searchText = ObservableField<String>()
    val isRecording = ObservableBoolean(false)
    val hint = ObservableField<CharSequence>("请按下面按钮说出要搜索的内容")
    var mView: IVoice? = null

    // 用HashMap存储听写结果
    val mIatResults = LinkedHashMap<String, String>()
    // 语音听写对象
    var mIat: SpeechRecognizer by Delegates.notNull()
    // 引擎类型
    val mEngineType = SpeechConstant.TYPE_CLOUD
    //监听器
    var mInitListener: InitListener? = null
    //听写UI监听器
    var mRecognizerDialogListener: RecognizerDialogListener? = null
    //听写监听器
    var mRecognizerListener: RecognizerListener by Delegates.notNull()
    // 函数调用返回值
    var ret: Int? = 0

    constructor(context: Context?, mView: IVoice) : this() {
        mContext = context
        this.mView = mView
        initListener()
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener)
    }

    /**
     * 初始化监听器。
     */
    fun initListener() {
        mInitListener = InitListener { code ->
            Log.d("tag", "SpeechRecognizer init() code = $code")
            if (code != ErrorCode.SUCCESS) {
                ToastUtil.setToast(mContext, "初始化失败，错误码：$code")
            }
        }
        mRecognizerDialogListener = object : RecognizerDialogListener {
            override fun onResult(results: RecognizerResult, isLast: Boolean) {
                setResult(results)
            }

            /**
             * 识别回调错误.
             */
            override fun onError(error: SpeechError) {
//            showTip(error.getPlainDescription(true))
                Log.e("tag", "err:${error.errorDescription},${error.errorCode}")
            }
        }
        mRecognizerListener = object : RecognizerListener {

            override fun onBeginOfSpeech() {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//                mContext?.toast("请开始说话")
//                isRecording.set(true)
//                Log.e("tag", "请开始说话")
            }

            override fun onError(error: SpeechError) {
                // Tips：
                // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
                isRecording.set(false)
                hint.set("您好像没有说话哦~")
                mContext?.toast(error.getPlainDescription(true))
            }

            override fun onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                hint.set("正在识别...")
            }

            override fun onResult(results: RecognizerResult, isLast: Boolean) {
                Log.e("tag", results.resultString)
                hint.set("")
                isRecording.set(false)
                setResult(results)

                if (isLast) {
                    //最后的结果
                }
            }

            override fun onVolumeChanged(volume: Int, data: ByteArray) {
//                showTip("当前正在说话，音量大小：$volume")
                Log.d("tag", "返回音频数据：" + data.size)
            }

            override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle) {
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                // 若使用本地能力，会话id为null
//                if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//                    val sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID)
//                    Log.e("tag", "session id =$sid")
//                }
            }
        }
    }


    /**
     * 听写UI监听器
     */


    private fun setResult(results: RecognizerResult) {
        Log.e("tag", "res:${results.resultString}")
        val text = parseIatResult(results.resultString)

        var sn = ""
        // 读取json结果中的sn字段
        try {
            val resultJson = JSONObject(results.resultString)
            sn = resultJson.optString("sn")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mIatResults[sn] = text

        val resultBuffer = StringBuffer()
        for (key in mIatResults.keys) {
            resultBuffer.append(mIatResults[key])
        }
        searchText.set(resultBuffer.toString())
    }

    fun close() {
        mView?.close()
    }

    fun startOrStop() {
        if (!isRecording.get()) {
            isRecording.set(true)
//            // 移动数据分析，收集开始听写事件
//            FlowerCollector.onEvent(mContext, "iat_recognize")
//            searchText.set("")
//            mIatResults.clear()
//            setParam()
////            mView?.startOrStop()
//            // 不显示听写对话框
//            ret = mIat.startListening(mRecognizerListener)
//            if (ret != ErrorCode.SUCCESS) {
//                mContext?.toast("听写失败,错误码：$ret")
//            } else {
//                mContext?.toast("请开始说话")
//            }
            mView?.start()
        } else {
            isRecording.set(false)
            mView?.cancel()
//            mIat?.stopListening()
        }
    }


    /**
     * 参数设置
     *
     * @return
     */
    fun setParam() {
        // 清空参数
//        mIat?.setParameter(SpeechConstant.PARAMS, null)

        // 设置听写引擎
        mIat?.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType)
        // 设置返回结果格式
        mIat?.setParameter(SpeechConstant.RESULT_TYPE, "json")

        // 设置语言
        mIat?.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
        // 设置结果返回语言
        mIat?.setParameter(SpeechConstant.ACCENT, "zh_cn")

        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat?.setParameter(SpeechConstant.VAD_BOS, "4000")

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat?.setParameter(SpeechConstant.VAD_EOS, "1000")

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat?.setParameter(SpeechConstant.ASR_PTT, "0")

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat?.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mIat?.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav")
    }

    fun parseIatResult(json: String): String {
        val ret = StringBuffer()
        try {
            val tokener = JSONTokener(json)
            val joResult = JSONObject(tokener)

            val words = joResult.getJSONArray("ws")
            for (i in 0 until words.length()) {
                // 转写结果词，默认使用第一个结果
                val items = words.getJSONObject(i).getJSONArray("cw")
                val obj = items.getJSONObject(0)
                ret.append(obj.getString("w"))
                //				如果需要多候选结果，解析数组其他字段
                //				for(int j = 0; j < items.length(); j++)
                //				{
                //					JSONObject obj = items.getJSONObject(j);
                //					ret.append(obj.getString("w"));
                //				}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ret.toString()
    }

    override fun onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(mContext?.javaClass?.simpleName)
        FlowerCollector.onPause(mContext)
        super.onPause()
    }

    override fun onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(mContext)
        FlowerCollector.onPageStart(mContext?.javaClass?.simpleName)
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 退出时释放连接
        mIat?.cancel()
        mIat?.destroy()
    }


}