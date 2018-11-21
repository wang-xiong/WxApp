package com.example.app_jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.IllegalFormatException;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        init();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());

        //1.属性访问
        tv.setText(updateTextFromC());

        //2.方法访问
        //tv.setText(getMethod());

        //3.数组访问
        getArray(source);
        for (int i = 0; i< source.length; i++) {
            Log.e("source:", source[i] + "");
        }

        //4.异常的捕获 java代码无法捕获c的异常,即无法try catch住的
        //如果jni层面出现异常，java的代码调用终止
        try {
            exception();
        } catch (Exception e) {
            Log.e("Exception:", e.getMessage());
        }

        //5.引用
        getLocalReference();

        //6.jni缓存策略
        for (int i=0; i<10; i++) {
            cached();
        }
    }

    public String name = "test";

    public String getName() {
        return "method";
    }

    private int[] source = {1, 10, 6, 4, 23};

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private native String updateTextFromC(); //属性访问

    private native String getMethod(); //方法访问

    private native void getArray(int[] array); //数组访问

    //引用作用通知Java回收JNI对象
    private native void getLocalReference(); //引用

    private native void exception(); //异常的处理

    private native void cached(); //缓存策略（第一种）

    private static native void init(); //缓存策略 (第二种)


}
