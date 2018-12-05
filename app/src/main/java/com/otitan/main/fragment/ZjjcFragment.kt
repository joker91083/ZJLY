package com.otitan.main.fragment

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.otitan.TitanApplication
import com.otitan.base.BaseFragment
import com.otitan.main.viewmodel.ZjjcViewModel
import com.otitan.ui.mview.IZjjc
import com.otitan.zjly.BR

import com.otitan.zjly.R
import com.otitan.zjly.databinding.FmZjjcBinding
import kotlinx.android.synthetic.main.fm_zjjc.*
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.ArrayList


/**
 *
 */
class ZjjcFragment : BaseFragment<FmZjjcBinding, ZjjcViewModel>(), IZjjc {

    var zjjcViewModel: ZjjcViewModel? = null
    val allPageModels = ArrayList<PageModel>()
    val pageModels = ArrayList<PageModel>()

    val titles = arrayOf("新闻动态", "绿化建设", "有害生物防治", "森林公安", "林权制度改革")


    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_zjjc
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): ZjjcViewModel {
        if (zjjcViewModel == null) {
            zjjcViewModel = ZjjcViewModel(activity, this)
        }
        return zjjcViewModel as ZjjcViewModel
    }


    companion object {
        private var zjjcFragment: ZjjcFragment? = null

        @JvmStatic
        @Synchronized
        fun getInstance(): ZjjcFragment? {
            if (zjjcFragment == null) {
                zjjcFragment = ZjjcFragment()
            }
            return zjjcFragment
        }
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbarResource)
        binding.toolbarResource.inflateMenu(R.menu.menu_edit)
        allPageModels.clear()
        pageModels.clear()
        titles.forEachWithIndex { i, title ->
            val model = PageModel()
            val f = NewListFragment()
            val bundle = Bundle()
            bundle.putInt("type", getType(title))
            f.arguments = bundle
            model.fm = f
            model.title = title
            allPageModels.add(model)
        }

        val selected = TitanApplication.sharedPreferences.getString("lable", "").split(",")//arrayOf("新闻动态", "绿化建设", "有害生物防治")
        if (selected.isEmpty()){
            val editor = TitanApplication.sharedPreferences.edit()
            val l = titles.toList().toString()
            val e = l.substring(1, l.lastIndex).replace(" ", "")
            editor.putString("lable", e)
            editor.apply()
        }
        allPageModels.forEach {
            if (selected.contains(it.title)) {
                pageModels.add(it)
            }
        }
        pageModels.sortBy { it.title }

        binding.pager.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): NewListFragment? {
                return pageModels[position].fm
            }

            override fun getCount(): Int {
                return pageModels.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return pageModels[position].title
            }

            override fun getItemPosition(`object`: Any): Int {
                return PagerAdapter.POSITION_NONE
            }

            override fun getItemId(position: Int): Long {
                return pageModels[position].hashCode().toLong()
            }
        }

        binding.tabLayout.setupWithViewPager(pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_edit -> {
                val list = ArrayList<String>()
                val intList = ArrayList<Int>()
                list.addAll(titles)

                val modelTitle = ArrayList<String>()
                pageModels.forEach { model ->
                    modelTitle.add(model.title)
                }
                list.forEachWithIndex { i, s ->
                    if (modelTitle.contains(s)) {
                        intList.add(i)
                    }
                }

                MaterialDialog.Builder(activity!!)
                        .title("标签选择")
                        .items(list)
                        .itemsCallbackMultiChoice(intList.toTypedArray(), object : MaterialDialog.ListCallbackMultiChoice {
                            override fun onSelection(dialog: MaterialDialog?, which: Array<out Int>?, text: Array<out CharSequence>?): Boolean {
                                pageModels.clear()
                                allPageModels.forEach {
                                    if (text?.contains(it.title) == true) {
                                        pageModels.add(it)
                                    }
                                }
                                pageModels.sortBy { it.title }

                                val editor = TitanApplication.sharedPreferences.edit()
                                val l = text?.toList().toString()
                                val e = l.substring(1, l.lastIndex).replace(" ", "")
                                editor.putString("lable", e)
                                editor.apply()
                                binding.pager.adapter?.notifyDataSetChanged()
                                return true
                            }
                        })
                        .positiveText("确定")
                        .onPositive { dialog, which ->
                            dialog.dismiss()
                        }.show()
            }
        }
        return true
    }

    fun getType(title: String): Int {
        var typy = 1
        when (title) {
            "新闻动态" -> typy = 1
            "绿化建设" -> typy = 2
            "有害生物防治" -> typy = 3
            "森林公安" -> typy = 4
            "林权制度改革" -> typy = 5
        }
        return typy
    }

    class PageModel {
        var title: String = ""
        var type: Int = 0
        var fm: NewListFragment? = null
    }
}
