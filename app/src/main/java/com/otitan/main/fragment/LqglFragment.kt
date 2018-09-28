package com.otitan.main.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.otitan.base.BaseFragment
import com.otitan.main.adapter.LqglAdapter
import com.otitan.main.viewmodel.LqglViewModel
import com.otitan.model.LQGLLable
import com.otitan.util.DividerGridItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLqglBinding
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.frag_lqgl.*
import org.jetbrains.anko.toast


/**
 *
 */
class LqglFragment : BaseFragment<FmLqglBinding, LqglViewModel>() {
    private val imgs = arrayOf(
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/35.jpg",
            "http://img2.3lian.com/2014/f2/37/d/34.jpg",
            "http://img2.3lian.com/2014/f2/37/d/36.jpg",
            "http://img2.3lian.com/2014/f2/37/d/37.jpg")
    private val mDrawables = arrayOf(
            R.drawable.icon_zllhcx, R.drawable.icon_zmsccx, R.drawable.icon_zyghqk, R.drawable.icon_zhfkqk,
            R.drawable.icon_lycf, R.drawable.icon_tghl,  R.drawable.icon_gyl,//R.drawable.icon_hmh,
            R.drawable.icon_sthly, R.drawable.icon_yzl, R.drawable.icon_slfh, R.drawable.icon_zmsc,
            R.drawable.icon_lyyhsw, R.drawable.icon_lycy, R.drawable.icon_lygg, R.drawable.icon_bhqgy)
    private val titles = arrayOf("标题1", "标题2", "标题3", "标题4", "标题5")

    var lqglViewModel: LqglViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_lqgl
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): LqglViewModel {
        if (lqglViewModel == null) {
            lqglViewModel = LqglViewModel(this)
        }
        return lqglViewModel as LqglViewModel
    }

    companion object {
        private var lqglFragment: LqglFragment? = null
        @JvmStatic

        @Synchronized
        fun getInstance(): Fragment? {
            if (lqglFragment == null) {
                lqglFragment = LqglFragment()
            }
            return lqglFragment
        }
    }

    override fun initData() {
        super.initData()
        //图片轮播
        activity?.picBanner?.setImages(imgs.toList())?.setImageLoader(MyImageLoader())
                ?.setDelayTime(3000)?.setBannerTitles(titles.toList())
                ?.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                ?.setIndicatorGravity(BannerConfig.RIGHT)?.start()

        activity?.picBanner?.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                activity?.toast(titles[position])
            }
        })
        //标签
        val list = ArrayList<LQGLLable>()
        val names = resources.getStringArray(R.array.home_main)
        for (i in mDrawables.indices) {
            val lable = LQGLLable()
            lable.drawable = mDrawables[i]
            lable.name = names[i]
            list.add(lable)
        }
        activity?.let {
            val adapter = LqglAdapter(it, list)
            val rvLqgl = it.rvLqgl
            val layoutManager = GridLayoutManager(it, 4)
            rvLqgl.layoutManager = layoutManager
            rvLqgl.addItemDecoration(DividerGridItemDecoration(it))
            rvLqgl.adapter = adapter
        }
    }

    class MyImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context).load(path).into(imageView)
        }
    }

}
