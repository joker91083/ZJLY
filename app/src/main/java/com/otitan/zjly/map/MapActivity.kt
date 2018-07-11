package com.otitan.zjly.map

import android.os.Bundle
import android.support.v4.app.Fragment
import com.otitan.zjly.R
import com.otitan.zjly.base.BaseActivity
import com.otitan.zjly.base.BaseViewModel
import com.otitan.zjly.base.Injection
import com.otitan.zjly.base.ViewModelHolder
import com.otitan.zjly.util.ActivityUtils

/**
 * Created by sp on 2018/7/10.
 * 地图
 */
class MapActivity : BaseActivity() {

    private var fragment: MapFragment? = null
    private var viewModel: MapViewModel? = null

//    private var mBackHandedFragment: BackHandledFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

//        mContext = this
        /*try {
            fragment = findOrCreateViewFragment() as MapFragment
            viewModel = findOrCreateViewModel() as MapViewModel
        } catch (e:Exception){
            Log.e("tag","地图信息获取异常$e")
        }*/
        fragment = findOrCreateViewFragment() as MapFragment
        viewModel = findOrCreateViewModel() as MapViewModel
        fragment!!.viewModel = viewModel
//        TitanApplication.addActivity(this)
    }

    override fun findOrCreateViewFragment(): Fragment {
        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_map)
        if (fragment == null) {
            fragment = MapFragment.getInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.fragment_map)
        }
        return fragment
    }

    override fun findOrCreateViewModel(): BaseViewModel {
        val holder = supportFragmentManager.findFragmentByTag(VIEWMODEL_TAG)
        if (holder == null || (holder as ViewModelHolder<*>).viewmodel == null) {
            val viewModel = MapViewModel(fragment, Injection.provideDataRepository(this))
            ActivityUtils.addFragmentToActivity(supportFragmentManager, ViewModelHolder.createContainer(viewModel), VIEWMODEL_TAG)
            return viewModel
        }
        return holder.viewmodel as BaseViewModel
    }
}