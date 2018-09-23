package com.otitan.main.viewmodel

import android.content.Context
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.data.Injection
import com.otitan.data.local.LocalDataSource
import com.otitan.util.FormatUtil
import com.otitan.zjly.R
import org.jetbrains.anko.toast

/**
 * Created by hanyw on 2018/2/3.
 * 登录
 */
class TrackManagerViewModel() : BaseViewModel() {


    private var mView: TrackManager? = null

    //开始时间
    var startValue=ObservableField<String>(FormatUtil.dateFormat())
    //结束时间
    var endValue=ObservableField<String>(FormatUtil.dateFormat())
    val mDataRepository = Injection.provideDataRepository()

    constructor(login: TrackManager, context: Context) : this() {
        this.mView = login
        this.mContext=context
    }
    /**
     * 确定
     */
    fun onConfirm() {
//        if(checkValue()){
//            val startime=FormatUtil.dateParse(startValue.get()!!)
//            val endtime=FormatUtil.dateParse(endValue.get()!!)
//            mDataRepository?.queryTrackPoint(startime, endtime,object : LocalDataSource.Callback{
//                override fun onFailure(info: String?) {
//                    LogUtil.logE("查询轨迹点异常$info")
//                    mView?.showToast(1,"查询轨迹点异常$info")
//                }
//                override fun onSuccess(result: Any?) {
//                    try {
//                        val trackpoints=result as MutableList<TrackPoint>
//                        if(trackpoints.isNotEmpty()){
//                            mView?.drawTrack(trackpoints)
//                        }else{
//                            mView?.showToast(1,"未查询到轨迹点")
//                        }
//
//                    }catch (e:Exception){
//                        LogUtil.logE("查询轨迹点异常$e")
//                        mView?.showToast(1,"查询轨迹点异常$e")
//                    }
//
//                }
//
//            })
//
//        }
    }

    /**
     * 检查条件
     */
    private fun checkValue() :Boolean{
        if(startValue.get() == ""){
            mContext?.toast(R.string.trackmanager_date1)
            return false
        }else if(endValue.get()==""){
            mContext?.toast(R.string.trackmanager_date2)
            return false

        }
        return true
    }

    /**
     * 取消
     */
    fun onConcel(){
        //mView?.showToast(1,"取消")
        mView?.closeDialog()
    }

    /**
     * 日期选择
     * @param type 1:开始时间 2:结束时间
     */
    fun onDateSelect(type: Int){
        mView?.showDateSelect(type)
    }





}