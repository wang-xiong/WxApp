package com.example.lib_mvvm.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ViewModelUtil {

    public static <T> Class<T> getInstance(Object object, int position) {
        if (object != null) {
            Type type = object.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                return (Class<T>) parameterizedType.getActualTypeArguments()[position];
            }
        }
        return null;
    }

    public static <T> T getNewInstance(Object object, int position) {
        Class<T> clazz = getInstance(object, position);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
