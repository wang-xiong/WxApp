package com.example.app_okhttp.okhttp;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class BaseCallBack<T> {
    public Type mType;

    private static Type getSuperclassTypeParameter(Class<?> subClass) {
        Type superClass = subClass.getGenericSuperclass();
        if (superClass instanceof Class) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public BaseCallBack() {
        mType = getSuperclassTypeParameter(this.getClass());
    }

    public void onSuccess(T t) {
    }

    public void onError(int code) {
    }

    public void onFailure(Call call, IOException e) {
    }

}
