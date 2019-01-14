package com.example.app_dagger2.annotation;

@ClassInfo("Test class")
public class TestRuntimeAnnotation {

    @FieldInfo(value =  {1,2})
    public String filedInfo = "FiledInfo";


    @MethodInfo(name = "My method", data = "hello word")
    public static String getMethodInfo() {
        return TestRuntimeAnnotation.class.getSimpleName();
    }
}
