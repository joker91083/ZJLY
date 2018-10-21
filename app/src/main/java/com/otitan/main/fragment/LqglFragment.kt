package com.otitan.main.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.otitan.TitanApplication
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
            "http://www.zjly.gov.cn/picture/-1/180927164748792150.png",
            "http://www.zjly.gov.cn/picture/-1/180929105544631156.jpg",
            "http://www.zjly.gov.cn/picture/-1/180926171837534393.png",
            "http://www.zjly.gov.cn/picture/-1/180926171836175726.jpg",
            "http://www.zjly.gov.cn/picture/-1/180921173026519382.png")
    private val mDrawables = arrayOf(
             R.drawable.icon_zmsccx, R.drawable.icon_zyghqk,
            R.drawable.icon_lycf,   R.drawable.icon_gyl,//R.drawable.icon_hmh,
            R.drawable.icon_sthly, R.drawable.icon_yzl, R.drawable.icon_slfh,
            R.drawable.icon_lyyhsw, R.drawable.icon_lycy, R.drawable.icon_lygg, R.drawable.icon_bhqgy)
    private val titles = arrayOf("杭州八大赏桂地 等你去寻香", "桐庐举办森林疗养国际研讨会",
            "全省林业新媒体应用培训班在杭州举办", "临安山核桃插上电商翅膀", "告别水泥森林 靠你喽森林城市群")

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
