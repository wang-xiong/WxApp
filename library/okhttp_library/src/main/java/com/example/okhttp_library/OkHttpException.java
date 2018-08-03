package com.example.okhttp_library;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class OkHttpException extends Exception {


    //自定义的异常类型
    public static final int NETWORK_ERROR = -1;
    public static final int JSON_ERROR = -2;
    public static final int OTHER_ERROR = -3;

    //与服务器约定的字段，如果有返回则http请求是成功的，但也有可能是业务逻辑的错误
    public static final String RESULT_CODE = "ecode";
    //约定字段的值
    public static final int RESULT_CODE_VALUE = 0;


    //与福利服务端约定的字段
    public static final String RESULT_WELFARE_CODE = "error";

    public static final String ERROR_MSG = "emsg";
    public static final String EMPTY_MSG = "";


    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
