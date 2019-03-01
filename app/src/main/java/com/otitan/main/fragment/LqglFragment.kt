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
import com.otitan.ui.mview.ILQGL
import com.otitan.util.DividerGridItemDecoration
import com.otitan.zjly.BR
import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmLqglBinding
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.frag_lqgl.*
import org.jetbrains.anko.toast


/**
 *
 */
class LqglFragment : BaseFragment<FmLqglBinding, LqglViewModel>(), ILQGL {
    private val mDrawables = arrayOf(
            R.drawable.icon_zyghqk, R.drawable.icon_slfh, R.drawable.icon_sthly,
            R.drawable.icon_ldzz, R.drawable.icon_yzl, R.drawable.icon_lyyhsw,
            R.drawable.icon_lygg, R.drawable.icon_bhqgy, R.drawable.icon_gyl,
            R.drawable.icon_zmsccx, R.drawable.icon_lquan, R.drawable.icon_tghl,
            R.drawable.icon_lycf, R.drawable.icon_lycy)//R.drawable.icon_hmh,

    var viewmodel: LqglViewModel? = null

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_lqgl
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): LqglViewModel {
        if (viewmodel == null) {
            viewmodel = LqglViewModel(activity, this)
        }
        return viewmodel as LqglViewModel
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

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun initData() {
        super.initData()
        binding.ftbLqgl.setOnClickListener { view ->
            startContainerActivity(VoiceSearchFragment::class.java.canonicalName)
        }
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

    override fun refresh() {
        activity?.picBanner?.setOnBannerListener { position ->
            val bundle = Bundle()
            bundle.putString("url", viewmodel?.data?.get(position)?.newsurl)
            startContainerActivity(NewFragment::class.java.canonicalName, bundle)
        }
        //图片轮播
        activity?.picBanner?.setImages(viewmodel?.imgs)?.setImageLoader(MyImageLoader())
                ?.setDelayTime(3000)?.setBannerTitles(viewmodel?.titles)
                ?.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                ?.setIndicatorGravity(BannerConfig.RIGHT)?.start()

    }
}
