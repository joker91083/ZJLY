package com.otitan.main.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.esri.arcgisruntime.geometry.Point
import com.jzxiang.pickerview.TimePickerDialog
import com.jzxiang.pickerview.data.Type
import com.jzxiang.pickerview.listener.OnDateSetListener
import com.otitan.main.model.TrackPoint
import com.otitan.main.viewmodel.TrackManager
import com.otitan.main.viewmodel.TrackManagerViewModel
import com.otitan.util.FormatUtil
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FragTrackBinding


/**
 * Created by hanyw on 2018/1/30
 * 轨迹管理
 */
class TrackManagerFragment : DialogFragment(), TrackManager, OnDateSetListener {


    var binding: FragTrackBinding?=null
    var mViewModel: TrackManagerViewModel?=null
    //日期类型
    private var dateType=1

    //时间选择
    private var mTimePickerDialog: TimePickerDialog? = null

    private var mListener:TrackManagerDialogListener?=null

    companion object {
        fun getInstance(): TrackManagerFragment {
            return TrackManagerFragment()
        }
    }

    interface TrackManagerDialogListener{
        fun drawTrackLine(list: List<TrackPoint>)
        fun dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //外部点击消失
        isCancelable=true

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragTrackBinding.inflate(inflater, container, false)
        binding?.viewmodel = mViewModel
        return binding!!.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = activity as TrackManagerDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(activity.toString() + " must implement TrackManagerDialogListener")
        }

    }




    override fun showDateSelect(type: Int) {
        //设置日期类型
        dateType=type
        if (mTimePickerDialog == null) {
            val fiveYears = 5L * 365 * 1000 * 60 * 60 * 24L
            val tenYears = 10L * 365 * 1000 * 60 * 60 * 24L
            mTimePickerDialog = TimePickerDialog.Builder()
                    .setCallBack(this)
                    .setCancelStringId("取消")
                    .setSureStringId("确定")
                    .setTitleStringId("时间选择")
                    .setYearText("年")
                    .setMonthText("月")
                    .setDayText("日")
                    .setHourText("时")
                    .setMinuteText("分")
                    .setThemeColor(resources.getColor(R.color.colorPrimary))
                    .setCyclic(false)
                    .setMinMillseconds(System.currentTimeMillis() - fiveYears)
                    .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                    .setCurrentMillseconds(System.currentTimeMillis())
                    .setType(Type.ALL)
                    .setWheelItemTextNormalColor(resources.getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(resources.getColor(R.color.colorAccent))
                    .setWheelItemTextSize(12)
                    .build()

        }
        mTimePickerDialog?.show(fragmentManager, "timepicker")

    }

    /**
     * 时间选择
     */
    override fun onDateSet(timePickerView: TimePickerDialog?, millseconds: Long) {
        var timeValue= FormatUtil.dateFormat(millseconds)
        when (dateType) {
            1 -> mViewModel?.startValue?.set(timeValue)
            2 -> mViewModel?.endValue?.set(timeValue)
        }
    }

    override fun closeDialog() {
        mListener?.dismiss()
    }

    override fun drawTrack(trackpoints: Any?) {
        this.dismiss()
        mListener?.drawTrackLine(trackpoints as List<TrackPoint>)
    }
}