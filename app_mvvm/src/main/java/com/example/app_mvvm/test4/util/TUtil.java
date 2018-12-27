package com.example.app_mvvm.test4.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TUtil {

    public static <T> Class<T> getInstance(Object object, int position) {
        if (object != null) {
            Type type = object.getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<T>) parameterizedType.getActualTypeArguments()[position];
        }
        return null;
    }

    public static <T> T getNewInstance(Object object, int position) {
        if (object != null) {
            try {
                Type type = object.getClass().getGenericSuperclass();
                ParameterizedType parameterizedType = (ParameterizedType) type;
                return ((Class<T>) parameterizedType.getActualTypeArguments()[position]).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
