package com.example.okhttp_library.Test;

import android.util.Log;

import com.example.okhttp_library.DisposeDataListener;
import com.example.okhttp_library.RequestCenter;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class Demo {
    private void  test() {
        RequestCenter.requestTestData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                TestModel model = (TestModel) responseObj;

            }

            @Override
            public void onFailure(Object responseObj) {
                Log.e("wx", "onFailure:" + responseObj.toString());
            }
        });
    }
}
