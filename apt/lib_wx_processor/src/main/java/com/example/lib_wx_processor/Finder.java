package com.example.lib_wx_processor;

import javax.lang.model.type.TypeMirror;

public class Finder {

    public static void addProvideMethod(Scope scope, ProvideMethod provideMethod) {
        for (TypeMirror typeMirror : scope.types()) {
            provideMethods.put(typeMirror, provideMethod);
        }
    }
}
