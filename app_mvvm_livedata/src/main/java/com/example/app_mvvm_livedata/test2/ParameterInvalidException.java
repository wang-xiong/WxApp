package com.example.app_mvvm_livedata.test2;

public class ParameterInvalidException extends BaseException {

    public ParameterInvalidException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "参数有误");
    }
}
