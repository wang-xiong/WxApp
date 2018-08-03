package com.example.module_app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.component_base.ARouter.RouterUrl;

/**
 * Created by wangxiong on 2018/7/20.
 */

public class RouterCenter {

    public static void toWelfare() {
        ARouter.getInstance().build(RouterUrl.PATH_MODULE_WELFARE_WELFARE).navigation();
    }
}
