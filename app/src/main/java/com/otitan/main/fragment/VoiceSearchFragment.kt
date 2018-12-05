package com.otitan.main.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.ui.RecognizerDialog
import com.otitan.baiduyuyin.recog.MyRecognizer
import com.otitan.baiduyuyin.recog.listener.MessageStatusRecogListener
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.VoiceSearchViewModel
import com.otitan.ui.mview.IVoice
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.baidu.speech.EventListener
import com.baidu.speech.asr.SpeechConstant
import com.otitan.baiduyuyin.recog.IStatus
import com.otitan.baiduyuyin.recog.RecogResult
import com.otitan.baiduyuyin.recog.listener.IRecogListener
import com.otitan.zjly.databinding.FmVoiceSearchBinding
import org.jetbrains.anko.toast

class VoiceSearchFragment : BaseFragment<FmVoiceSearchBinding, VoiceSearchViewModel>(),
        InitListener, IVoice, EventListener {

    var viewmodel: VoiceSearchViewModel? = null
    // 语音听写UI
    private var mIatDialog: RecognizerDialog? = null
    var handler: Handler? = null
    /**
     * 识别控制器，使用MyRecognizer控制识别的流程
     */
    var myRecognizer: MyRecognizer? = null
    var listener: IRecogListener? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_voice_search
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): VoiceSearchViewModel {
        if (viewmodel == null) {
            viewmodel = VoiceSearchViewModel(activity, this)
        }
        return viewmodel as VoiceSearchViewModel
    }

    override fun initData() {
        super.initData()
//        val dialog = RecognizerDialog(activity, this)
//        viewmodel?.startOrStop()

        initHandler()
        // DEMO集成步骤 1.1 新建一个回调类，识别引擎会回调这个类告知重要状态和识别结果
        listener = MessageStatusRecogListener(handler)
        // DEMO集成步骤 1.2 初始化：new一个IRecogListener示例 & new 一个 MyRecognizer 示例
        myRecognizer = MyRecognizer(activity, listener)
    }

    fun initHandler() {
        if (handler == null) {
            handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg)
                    when (msg?.arg1) {
                        IStatus.STATUS_NONE -> {
                            viewmodel?.hint?.set(msg.obj.toString())
                        }
                        IStatus.STATUS_READY -> {
//                            viewmodel?.hint?.set(msg.obj.toString())
                            activity?.toast(msg.obj.toString())
                        }
                        IStatus.STATUS_SPEAKING -> {
                            viewmodel?.hint?.set(msg.obj.toString())
                        }
                        IStatus.STATUS_RECOGNITION -> {
                            viewmodel?.hint?.set(msg.obj.toString())
                        }
                        IStatus.STATUS_FINISHED -> {
//                            viewmodel?.hint?.set("正在搜索...")
                            binding.etSearch.setText(msg.obj.toString())
                            viewmodel?.isRecording?.set(false)
                        }
                    }
                }
            }
        }
    }

    override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
        if (name == SpeechConstant.CALLBACK_EVENT_ASR_READY) {
            // 引擎准备就绪，可以开始说话
            listener?.onAsrReady()
        } else if (name == SpeechConstant.CALLBACK_EVENT_ASR_BEGIN) {
            // 检测到用户的已经开始说话
            listener?.onAsrBegin()
        } else if (name == SpeechConstant.CALLBACK_EVENT_ASR_END) {
            // 检测到用户的已经停止说话
            listener?.onAsrEnd()
        } else if (name == SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL) {
            val recogResult = RecogResult.parseJson(params)
            // 临时识别结果, 长语音模式需要从此消息中取出结果
            val results = recogResult.resultsRecognition
            if (recogResult.isFinalResult) {
                listener?.onAsrFinalResult(results, recogResult)
            }

        } else if (name == SpeechConstant.CALLBACK_EVENT_ASR_FINISH) {
            // 识别结束， 最终识别结果或可能的错误
            val recogResult = RecogResult.parseJson(params)
            if (recogResult.hasError()) {
                val errorCode = recogResult.error
                val subErrorCode = recogResult.subError
                Log.e("tag", "asr error:$params")
                listener?.onAsrFinishError(errorCode, subErrorCode, recogResult.desc, recogResult)
            } else {
                listener?.onAsrFinish(recogResult)
            }
        } else if (name == SpeechConstant.CALLBACK_EVENT_ASR_EXIT) {
            listener?.onAsrExit()
        }
    }

    override fun onInit(p0: Int) {

    }

    override fun close() {
        this.onBackPressed()
    }

    override fun start() {
        viewmodel?.hint?.set("正在聆听...")
        val params = HashMap<String, Any>()
        params["accept-audio-volume"] = false
        // DEMO集成步骤2.2 开始识别
        myRecognizer?.start(params)
    }

    override fun stop() {
        // DEMO集成步骤4 (可选） 停止录音
        viewmodel?.hint?.set("停止识别")
        myRecognizer?.stop()
    }

    override fun cancel() {
        // DEMO集成步骤5 (可选） 取消本次识别
        myRecognizer?.cancel()
    }

    override fun startOrStop() {
        if (viewmodel?.isRecording?.get() == true) {
            // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
            // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
//            mIatDialog = RecognizerDialog(activity, viewmodel?.mInitListener)
//            // 显示听写对话框
//            mIatDialog?.setListener(viewmodel?.mRecognizerDialogListener)
//            mIatDialog?.show()
        }
    }

    override fun onDestroy() {
        // DEMO集成步骤3 释放资源
        // 如果之前调用过myRecognizer.loadOfflineEngine()， release()里会自动调用释放离线资源
        myRecognizer?.release()
        Log.i("tag", "onDestory")
        super.onDestroy()
    }
}