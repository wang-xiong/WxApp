package com.example.homemodule.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.component_base.ARouter.RouterUrl;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class RouterCenter {


    public  static  void toCommonActivity() {
        ARouter.getInstance().build(RouterUrl.PATH_COMMON_UI_HOME).navigation();
    }

    public static  void toAppMvpTestActivity() {
        ARouter.getInstance().build(RouterUrl.PATH_MODULE_MVP_TEST).navigation();
    }

    public static void toToolBarActivity() {
        ARouter.getInstance().build(RouterUrl.PATH_MODULE_A_TOOLBAR).navigation();
    }

    public static  void toAppMainActivity() {
        ARouter.getInstance().build(RouterUrl.PATH_MODULE_APP_MAIN).navigation();
    }

}
