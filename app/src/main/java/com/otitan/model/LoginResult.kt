package com.otitan.model

import java.io.Serializable

class LoginResult : Serializable {
    var token: Token? = null
    var code: String? = null
    var menu: Menu? = null

    //登录标识
    //    private String access_token;
    //    //标识类型
    //    private String token_type;
    //    private int expires_in;
    //    private String refresh_token;
    //    private String error;
    //    private String error_description;

    var access_token: String?
        get() = token?.access_token
        set(access_token) {
            token?.access_token = access_token
        }

    var token_type: String?
        get() = token?.token_type
        set(token_type) {
            token?.token_type = token_type
        }

    var expires_in: Int?
        get() = token?.expires_in
        set(expires_in) {
            token?.expires_in = expires_in
        }

    var refresh_token: String?
        get() = token?.refresh_token
        set(refresh_token) {
            token?.refresh_token = refresh_token
        }

    var error: String?
        get() = token?.error
        set(error) {
            token?.error = error
        }

    var error_description: String?
        get() = token?.error_description
        set(error_description) {
            token?.error_description = error_description
        }

    class Token {
        //登录标识
        var access_token: String? = null
        //标识类型
        var token_type: String? = null
        var expires_in: Int? = 0
        var refresh_token: String? = null
        var error: String? = null
        var error_description: String? = null
    }

    class Menu {
        //林业巡检
        var APP_LYXJ: Boolean = false
        //图层控制
        var APP_LYXJ_TCKZ: Boolean = false
        //事件上报
        var APP_LYXJ_SJSB: Boolean = false
        //小班编辑
        var APP_LYXJ_XBBJ: Boolean = false
        //林情概览
        var APP_LQGL: Boolean = false
        //资源管理
        var APP_LQGL_ZYGL: Boolean = false
        //资源管理 数据
        var APP_LQGL_ZYGL_SJCX: Boolean = false
        //森林防火
        var APP_LQGL_SLFH: Boolean = false
        var APP_LQGL_SLFH_SJCX: Boolean = false
        //行政执法
        var APP_LQGL_XZZF: Boolean = false
        var APP_LQGL_XZZF_SJCX: Boolean = false
        //林地征占
        var APP_LQGL_LDZZ: Boolean = false
        var APP_LQGL_LDZZ_SJCX: Boolean = false
        //营造林
        var APP_LQGL_YZL: Boolean = false
        var APP_LQGL_YZL_SJCX: Boolean = false
        //有害生物
        var APP_LQGL_YHSW: Boolean = false
        var APP_LQGL_YHSW_SJCX: Boolean = false
        //国有林场
        var APP_LQGL_GYLC: Boolean = false
        var APP_LQGL_GYLC_SJCX: Boolean = false
        //森林公园
        var APP_LQGL_SLGY: Boolean = false
        var APP_LQGL_SLGY_SJCX: Boolean = false
        //湿地保护
        var APP_LQGL_SDBH: Boolean = false
        var APP_LQGL_SDBH_SJCX: Boolean = false
        //林业科技
        var APP_LQGL_LYKJ: Boolean = false
        //林权
        var APP_LQGL_LQ: Boolean = false
        var APP_LQGL_LQ_SJCX: Boolean = false
        //植物检疫
        var APP_LQGL_ZWJY: Boolean = false
        var APP_LQGL_ZWJY_SJCX: Boolean = false
        //采伐运输
        var APP_LQGL_CFYS: Boolean = false
        var APP_LQGL_CFYS_SJCX: Boolean = false
        //林业产业
        var APP_LQGL_LYCY: Boolean = false
        var APP_LQGL_LYCY_SJCX: Boolean = false
    }

    companion object {

        private const val serialVersionUID = 5863574137745274049L
    }
}
