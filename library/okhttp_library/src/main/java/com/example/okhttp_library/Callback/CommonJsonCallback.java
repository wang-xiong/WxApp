package com.example.okhttp_library.Callback;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.okhttp_library.DisposeDataHandle;
import com.example.okhttp_library.DisposeDataListener;
import com.example.okhttp_library.OkHttpException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.okhttp_library.OkHttpException.EMPTY_MSG;
import static com.example.okhttp_library.OkHttpException.JSON_ERROR;
import static com.example.okhttp_library.OkHttpException.NETWORK_ERROR;
import static com.example.okhttp_library.OkHttpException.OTHER_ERROR;
import static com.example.okhttp_library.OkHttpException.RESULT_CODE;
import static com.example.okhttp_library.OkHttpException.RESULT_CODE_VALUE;
import static com.example.okhttp_library.OkHttpException.RESULT_WELFARE_CODE;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class CommonJsonCallback implements Callback {

    private static final String TAG = CommonJsonCallback.class.getSimpleName();
    private Handler mHandler;
    private DisposeDataListener mDisposeDataListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mDisposeDataListener = handle.mListener;
        this.mClass = handle.mClass;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mDisposeDataListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        handleResponse(response);
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void handleResponse(Response response) {
        /**
         * 1.坑点response.body().string()与response.body().toString()写错
         * 2.response.body().string()只能调用一次，多次调用会出现异常java.lang.IllegalStateException: closed
         */
        try {
            if (mClass == null) {
                mDisposeDataListener.onSuccess(response);
            } else {
                String responseStr = response.body().string();

                Object object = new Gson().fromJson(responseStr, mClass);
                if (object != null) {
                    mDisposeDataListener.onSuccess(object);
                } else {
                    mDisposeDataListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            mDisposeDataListener.onFailure(new OkHttpException(JSON_ERROR, e.getMessage()));
        }

        try {


            /*String responseStr = response.body().string();
            if (responseStr == responseStr || responseStr.trim().equals("")) {
                mDisposeDataListener.onFailure(new OkHttpException(OTHER_ERROR, EMPTY_MSG));
            }
            JSONObject result = new JSONObject(responseStr);
            if (result.has(RESULT_CODE)) {
                if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                    if (mClass == null) {
                        mDisposeDataListener.onSuccess(response);
                    } else {
                        Object object = new Gson().fromJson(result.toString(), mClass);
                        if (object != null) {
                            mDisposeDataListener.onSuccess(object);
                        } else {
                            mDisposeDataListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                } else {
                    mDisposeDataListener.onFailure(new OkHttpException(OTHER_ERROR, result.getInt(RESULT_CODE)));
                }
            } else if (result.has(RESULT_WELFARE_CODE)) {
                //检测result_code
                if (result.getBoolean(RESULT_WELFARE_CODE)) {
                    //返回数据不满足
                    mDisposeDataListener.onFailure(new OkHttpException(OTHER_ERROR, result.getBoolean(RESULT_WELFARE_CODE)));
                } else {
                    if (mClass != null) {
                        //JsonModel存在，解析json数据
                        Object object = new Gson().fromJson(responseStr, mClass);
                        if (object != null) {
                            //解析成功
                            mDisposeDataListener.onSuccess(object);
                        } else {
                            //解析成功
                            mDisposeDataListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    } else {
                        mDisposeDataListener.onSuccess(response);
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            mDisposeDataListener.onFailure(new OkHttpException(JSON_ERROR, e.getMessage()));
        }
    }

}
