package com.otitan.model

/**
 * 返回的错误信息
 */
class ErrResultModel<T> {
    var responseResult: Boolean? = false
    var responseMsg: String? = ""
    var responseCode: Int? = 0
    var data: T? = null
}