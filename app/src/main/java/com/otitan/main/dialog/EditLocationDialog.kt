package com.otitan.main.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.geometry.Point
import com.github.mikephil.charting.utils.Utils.convertDpToPixel
import com.otitan.base.BaseDialogFragment
import com.otitan.main.viewmodel.EditLocationViewModel
import com.otitan.ui.mview.IEditLocation
import com.otitan.util.FormatUtil
import com.otitan.util.SpatialUtil
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.DialogLocEditBinding
import java.math.BigDecimal

/**
 * 手动定位坐标
 */
class EditLocationDialog : BaseDialogFragment<DialogLocEditBinding, EditLocationViewModel>(), IEditLocation {

    var viewmodel: EditLocationViewModel? = null
    var listener: IEditLoc? = null
    var locPoint: Point? = null

    object Holder {
        val instance = EditLocationDialog()
    }

    companion object {
        val instance: EditLocationDialog = Holder.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onStart() {
        super.onStart()
        setParams(0)
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.dialog_loc_edit
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): EditLocationViewModel {
        if (viewmodel == null) {
            viewmodel = EditLocationViewModel(activity, this)
        }
        return viewmodel as EditLocationViewModel
    }

    override fun initData() {
        super.initData()
        val p = viewmodel?.loc?.get()
        p?.x = BigDecimal(locPoint?.x?.toString() ?: "0.0").toString()
        p?.y = BigDecimal(locPoint?.y?.toString() ?: "0.0").toString()
        if (locPoint != null) {
            val point = GeometryEngine.project(locPoint, SpatialUtil.spatialWgs4326) as Point
            val x = point.x
            val y = point.y
            p?.lon = x.toString()
            p?.lat = y.toString()
            val arrX = x.toString().split(".")
            val fenX = ((("0." + arrX[1]).toDouble()) * 60).toString().split(".")
            val arrY = y.toString().split(".")
            val fenY = ((("0." + arrY[1]).toDouble()) * 60).toString().split(".")
            p?.lonD = arrX[0]
            p?.lonF = fenX[0]
            p?.lonM = FormatUtil.LocFormat((("0." + fenX[1]).toDouble()) * 60).toString()
            p?.latD = arrY[0]
            p?.latF = fenY[0]
            p?.latM = FormatUtil.LocFormat((("0." + fenY[1]).toDouble()) * 60).toString()
        }
        p?.locLon = BigDecimal(locPoint?.x?.toString() ?: "0.0").toString()
        p?.locLat = BigDecimal(locPoint?.y?.toString() ?: "0.0").toString()
    }

    fun setParams(height: Int) {
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            if (window != null) {
                window.setGravity(Gravity.TOP)
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//设置dialog背景透明
                val lp = window.attributes
                lp.width = resources.displayMetrics.widthPixels
//                lp.height = (resources.displayMetrics.heightPixels * 0.4).toInt()
                //                if (height==0){
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
                //                }else {
                //                    int h = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getActivity().getResources().getDisplayMetrics());
                //                    lp.height = height+h;
                //                }
                window.attributes = lp
                val params = view!!.layoutParams
                val params1 = params as ViewGroup.MarginLayoutParams
                params1.topMargin = convertDpToPixel(22f).toInt()
                view!!.layoutParams = params1
            }
            //            DisplayMetrics dm = new DisplayMetrics();
            //            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            //            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.25), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    override fun sure(point: Point) {
        listener?.setPoint(point)
        dialog.dismiss()
    }

    override fun cancel() {
        dialog.dismiss()
    }

    interface IEditLoc {
        fun setPoint(point: Point)
    }
}