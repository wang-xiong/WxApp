package com.example.wangxiong.wxmoduletest.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.component_base.ARouter.RouterUrl;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class RouterCenter {
    public static void toHomeActivity() {
        ARouter.getInstance().build(RouterUrl.PATH_MODULE_HOME_HOME).navigation();
    }
}
