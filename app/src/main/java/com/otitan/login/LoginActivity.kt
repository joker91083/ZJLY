package com.otitan.login

import android.os.Bundle
import android.support.v4.app.Fragment
import com.otitan.TitanApplication
import com.otitan.base.ActivityUtils
import com.otitan.base.BaseFragmentActivity
import com.otitan.base.BaseViewModel
import com.otitan.base.ViewModelHolder
import com.otitan.permissions.PermissionsActivity
import com.otitan.permissions.PermissionsChecker
import com.otitan.util.Constant
import com.otitan.util.ToastUtil
import com.otitan.zjly.R
import com.titan.versionupdata.VersionUpdata


class LoginActivity : BaseFragmentActivity() {

    private var loginFragment: LoginFragment? = null
    private var loginModel: LoginModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginFragment = findOrCreateViewFragment() as LoginFragment
        loginModel = findOrCreateViewModel() as LoginModel
        loginFragment?.loginModel = loginModel!!

        var checked = TitanApplication.sharedPreferences.getBoolean("remember",true)
        if(checked){
            var name = TitanApplication.sharedPreferences.getString("name","")
            var password = TitanApplication.sharedPreferences.getString("password","")
            loginModel!!.name?.set(name)
            loginModel!!.password?.set(password)
        }

    }

    override fun findOrCreateViewFragment(): Fragment {

        var fragment = supportFragmentManager.findFragmentById(R.id.login_frame)
        if(fragment == null){
            fragment = LoginFragment.getInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.login_frame)
        }
        return fragment
    }

    override fun findOrCreateViewModel(): BaseViewModel {
        var holder = supportFragmentManager.findFragmentByTag(TAG)
        if(holder == null || (holder as ViewModelHolder<*>).viewmodel == null){
            var viewModel = LoginModel(this)
            ActivityUtils.addFragmentToActivity(supportFragmentManager, ViewModelHolder.createContainer(viewModel), TAG)
            return viewModel
        }
        return holder.viewmodel as BaseViewModel
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onStart() {
        super.onStart()
        var flag = PermissionsChecker(this).lacksPermissions(*Constant.permissions)
        if (flag) {
            PermissionsActivity.startActivityForResult(this, Constant.PERMISSIONS_REQUEST_CODE, *Constant.permissions)
        }

        checkUpdata()
    }

    /*版本更新*/
    fun checkUpdata(){
        var url = this.getString(R.string.updata_url)
        var flag = VersionUpdata(loginFragment!!.activity).checkVersion(url)
        if(!flag){
            ToastUtil.setToast(this.mContext,"已经是最新版本")
        }
    }

}
