package com.otitan.main.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.lling.photopicker.PhotoPickerActivity
import com.otitan.base.BaseFragment
import com.otitan.main.adapter.Recyc_imageAdapter
import com.otitan.main.model.ActionModel
import com.otitan.main.viewmodel.UpEventViewModel
import com.otitan.permissions.PermissionsActivity
import com.otitan.permissions.PermissionsChecker
import com.otitan.ui.mview.IUpEvent
import com.otitan.util.Constant
import com.otitan.util.ScreenTool
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmUpEventBinding
import com.otitan.zjly.util.MaterialDialogUtil
import com.titan.baselibrary.util.ToastUtil
import com.titan.medialibrary.activity.AudioRecorderActivity
import com.titan.medialibrary.activity.VideoRecorderActivity
import java.io.File

/**
 * 事件上报
 */
class UpEventFragment : BaseFragment<FmUpEventBinding, UpEventViewModel>(), IUpEvent {

    var viewmodel: UpEventViewModel? = null
    val PICK_PHOTO = 0x000003
    val PICK_AUDIO = 0x000004
    val PICK_VIDEO = 0x000005

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_up_event
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): UpEventViewModel {
        if (viewmodel == null) {
            viewmodel = UpEventViewModel(activity, this)
        }
        return viewmodel as UpEventViewModel
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        binding.toolbar.title = "事件上报"
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }

        val bundle = arguments
        if (bundle != null) {
            val event = viewmodel?.event?.get()
            event?.LAT = bundle.getString("lat")
            event?.LON = bundle.getString("lon")
            viewmodel?.event?.set(event)
            viewmodel?.notifyChange()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_event_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.event_list -> {
                startContainerActivity(EventListFragment::class.java.canonicalName)
            }
        }
        return true
    }

    override fun selectPic() {
        // 缺少权限时, 进入权限配置页面
        if (PermissionsChecker(this.activity!!).lacksPermissions(*Constant.CAMERA)) {
            PermissionsActivity.startActivityForResult(this.activity, PermissionsActivity.PERMISSIONS_REQUEST_CODE, *Constant.CAMERA)
            return
        }
        if (viewmodel?.picList?.size != 0 && viewmodel?.picList?.size != 9 && activity != null) {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("重新选择会覆盖之前的图片")
            builder.setMessage("是否重新选择")
            builder.setCancelable(true)
            builder.setPositiveButton("是") { dialog, id ->
                viewmodel?.picList?.clear()
                val intent = Intent(activity, PhotoPickerActivity::class.java)
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true)//是否显示相机
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI)//选择模式（默认多选模式）
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM)//最大照片张数
                this.startActivityForResult(intent, PICK_PHOTO)
            }
            builder.setNegativeButton("否") { dialog, id ->
                val intent = Intent(activity, PhotoPickerActivity::class.java)
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true)//是否显示相机
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI)//选择模式（默认多选模式）
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM)//最大照片张数
                this.startActivityForResult(intent, PICK_PHOTO)
            }
            val dialog = builder.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).textSize = 16f
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).textSize = 16f
        }
        if (viewmodel?.picList?.size == 9) {
            ToastUtil.setToast(activity, "照片最多只能选择9张")
            return
        }
        if (viewmodel?.picList?.size == 0) {
            val intent = Intent(activity, PhotoPickerActivity::class.java)
            intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true)//是否显示相机
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI)//选择模式（默认多选模式）
            intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM)//最大照片张数
            startActivityForResult(intent, PICK_PHOTO)
        }
    }

    override fun selectAudio() {
        // 缺少权限时, 进入权限配置页面
        if (PermissionsChecker(this.activity!!).lacksPermissions(*Constant.AUDIO)) {
            PermissionsActivity.startActivityForResult(this.activity, PermissionsActivity.PERMISSIONS_REQUEST_CODE, *Constant.CAMERA)
            return
        }
        val intent = Intent(activity, AudioRecorderActivity::class.java)
        startActivityForResult(intent, PICK_AUDIO)
    }

    override fun selectVideo() {
        // 缺少权限时, 进入权限配置页面
        if (PermissionsChecker(this.activity!!).lacksPermissions(*Constant.CAMERA, *Constant.AUDIO)) {
            PermissionsActivity.startActivityForResult(this.activity, PermissionsActivity.PERMISSIONS_REQUEST_CODE, *Constant.CAMERA)
            return
        }
        val intent = Intent(activity, VideoRecorderActivity::class.java)
        startActivityForResult(intent, PICK_VIDEO)
    }

    override fun selectType() {
        if (activity != null) {
            val array = resources.getStringArray(R.array.up_event_type)
            val list = ArrayList<String>()
            list.addAll(array)
            val dialog = MaterialDialogUtil.showSingleSelectionDialog(activity!!, "事件类型", list,
                    MaterialDialog.ListCallback { dialog, itemView, position, text ->
                        binding.edSjlx.setText(text)
                        viewmodel?.event?.get()?.SJLX = position
                        dialog.dismiss()
                    })
            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_PHOTO -> {
                if (data != null) {
                    //图片选择成功
                    val list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT)
                    viewmodel?.picList?.addAll(list)
                    loadPhoto()
                }
            }
            PICK_AUDIO -> {
                if (data != null) {
                    val path = data.getStringExtra(AudioRecorderActivity.KEY_RESULT)
                    viewmodel?.audioPath = path
                    binding.txtXcyp.text = File(path).name
                }
            }
            PICK_VIDEO -> {
                if (data != null) {
                    val path = data.getStringExtra(VideoRecorderActivity.KEY_RESULT)
                    viewmodel?.videoPath = path
                    binding.txtXcsp.setVideoPath(path)
                    binding.txtXcsp.start()
                }
            }
        }
    }


    /**
     * 选择图片后加载图片
     */
    fun loadPhoto() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.txtXczp.layoutManager = layoutManager
        val adapter = Recyc_imageAdapter(activity, viewmodel?.picList, ScreenTool.getScreenPix(activity).widthPixels / 4)
        binding.txtXczp.adapter = adapter
    }
}