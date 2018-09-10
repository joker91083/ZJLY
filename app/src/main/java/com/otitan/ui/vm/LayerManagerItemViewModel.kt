package com.otitan.ui.vm

import android.app.Activity
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.otitan.base.BaseViewModel
import com.otitan.ui.mview.ILayerManagerItem
import org.jetbrains.anko.toast
import java.io.File
import kotlin.properties.Delegates

class LayerManagerItemViewModel() : BaseViewModel() {

    val checked = ObservableBoolean(false)
    var checkedMap: HashMap<String, Boolean>? = null
    val title = ObservableField<String>("")
    var file: File by Delegates.notNull()
    var activity: Activity by Delegates.notNull()
    var mView: ILayerManagerItem by Delegates.notNull()

    constructor(activity: Activity, mView: ILayerManagerItem) : this() {
        this.activity = activity
        this.mView = mView
    }

    fun addLayer() {
        if (!file.exists()) {
            activity.toast("图层文件不存在")
            checked.set(!checked.get())
            return
        }
        checkedMap?.put(file.absolutePath, checked.get())
        mView.addLayer(file, checked.get())
    }

    fun setExtent() {
        if (checked.get()) {
            mView.setExtent(file)
        }
    }
}