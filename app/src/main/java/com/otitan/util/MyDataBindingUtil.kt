package com.otitan.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import android.widget.Spinner
import com.bumptech.glide.Glide

/**
 * Created by hanyw on 2018/3/27.
 * 自定义databinding图片绑定帮助类
 */
class MyDataBindingUtil() {
    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, bitmap: Bitmap) {
        view.setImageBitmap(bitmap)
    }

    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    @BindingAdapter("app:imageUrl")
    fun loadImage(imageView: ImageView, url: String) {
        var path = url
        if (url.contains("/storage/")){
            path = "file://$url"
        }
//        val uri= Uri.parse(path)
//        val request = ImageRequestBuilder.newBuilderWithSource(uri)
//                .setResizeOptions(ResizeOptions(80,80)).build()
//        val controller = Fresco.newDraweeControllerBuilder()
//                .setOldController(imageView.controller)
//                .setImageRequest(request)
//                .build()
//
//        imageView.controller = controller
//        if (url.contains("/storage/")){
//            imageView.setImageURI("file://$url")
//            return
//        }
//        imageView.setImageURI(url)

    }

    @BindingAdapter("app:resource")
    fun loadImage(imageView: ImageView,source:Int){
        Glide.with(imageView.context).load(source).into(imageView)
    }

    @BindingAdapter("app:selection")
    fun setSelection(spinner: Spinner, position: Int) {
        spinner.setSelection(position)
    }
}