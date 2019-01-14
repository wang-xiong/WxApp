package com.example.app_dagger2.annotation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.app_dagger2.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AnnotationTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        testAnnotation();
    }

    private void testAnnotation() {
        StringBuilder sb =  new StringBuilder();
        Class<?> clazz = TestRuntimeAnnotation.class;
        Constructor<?>[] constructor = clazz.getConstructors();

        sb.append("\n");
        sb.append("ClassInfo注解：").append("\n");

        ClassInfo classInfo = clazz.getAnnotation(ClassInfo.class);
        if (classInfo != null) {
            sb.append(Modifier.toString(clazz.getModifiers()))
                    .append("   ")
                    .append(clazz.getSimpleName())
                    .append("\n");
            sb.append("ClassInfo注解值：")
                    .append(classInfo.value())
                    .append("\n\n");
        }

        sb.append("FieldInfo注解：").append("\n");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
            if (fieldInfo  != null) {
                sb.append(Modifier.toString(field.getModifiers()))
                        .append("  ")
                        .append(field.getType().getSimpleName())
                        .append("  ")
                        .append(field.getName())
                        .append("\n");
                sb.append("FieldInfo注解值：")
                        .append(Arrays.toString(fieldInfo.value()))
                        .append("\n\n");
            }
        }

        sb.append("MethodInfo注解：").append("\n");

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
            if (methodInfo != null) {
                sb.append(Modifier.toString(method.getModifiers()))
                        .append("  ")
                        .append(method.getReturnType().getSimpleName())
                        .append("  ")
                        .append(method.getName())
                        .append("\n");
                sb.append("MethodInfo注解值：").append("\n")
                        .append("name:").append(methodInfo.name()).append("\n")
                        .append("data:").append(methodInfo.data()).append("\n")
                        .append("age:").append(methodInfo.age()).append("\n")
                        .append("\n\n");
            }
        }


        Log.e("AnnotationTestActivity", sb.toString());
    }
}


