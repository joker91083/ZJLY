package com.otitan.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.otitan.util.Utils
import java.lang.ref.WeakReference
import kotlin.properties.Delegates
import android.os.Build
import android.view.View


class ContainerActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT = "fragment"
        const val BUNDLE = "bundle"
    }

    protected var mFragment: WeakReference<Fragment>? = null
    private var mainLayout: ViewGroup by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        mainLayout = LinearLayout(this)
        mainLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        mainLayout.id = Utils.generateViewId()
        setContentView(mainLayout)
        val fm = supportFragmentManager
        val fragment = fm.findFragmentById(mainLayout.id)
        if (fragment == null) {
            initFromIntent(intent)
        }
    }

    private fun initFromIntent(intent: Intent?) {
        if (intent == null) {
            throw RuntimeException("you must provide a page info to display")
        }
        try {
            val fragmentName = intent.getStringExtra(FRAGMENT)
            if (fragmentName == null || fragmentName == "") {
                throw IllegalArgumentException("can not find page fragmentName")
            }
            val fragmentClass = Class.forName(fragmentName)
            val baseFragment = fragmentClass.newInstance() as BaseFragment<*, *>
            val args = intent.getBundleExtra(BUNDLE)
            if (args != null) {
                baseFragment.arguments = args
            }
            val trans = supportFragmentManager.beginTransaction()
            trans.replace(mainLayout.id, baseFragment)
            trans.commitAllowingStateLoss()
            mFragment = WeakReference(baseFragment)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(mainLayout.id)
        if (fragment is BaseFragment<*, *>) {
            if (!fragment.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }

    }
}