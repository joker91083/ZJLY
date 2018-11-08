package com.otitan.model;

import java.io.Serializable;

/**
 * 网络请求返回数据
 * @param <T>
 */
public class ResultModel<T> implements Serializable {
    private static final long serialVersionUID = 8494124052055734528L;
    private boolean ResponseResult;
    private String ResponseMsg;
    private int ResponseCode;
    private T Data;

    public boolean isResponseResult() {
        return ResponseResult;
    }

    public void setResponseResult(boolean responseResult) {
        ResponseResult = responseResult;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
    }

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }


    public class Err{
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
