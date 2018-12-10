package com.example.app_okhttp.okdown;

import android.content.Context;

import com.example.app_okhttp.okdown.download.OkDownBuilder;


/**
 * Created by hongkl on 17/8/21.
 */
public class OkDown {
    /**
     * 下载
     *
     * @param context
     * @return
     */
    public static OkDownBuilder init(Context context) {
        return new OkDownBuilder(context);
    }

}
