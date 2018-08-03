package com.example.module_welfare.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.component_base.ARouter.RouterIntentKV;
import com.example.component_base.ARouter.RouterUrl;
import com.example.module_welfare.bean.PreviewBean;

import java.util.ArrayList;

/**
 * Created by wangxiong on 2018/7/22.
 */

public class RouterCenter {
    public static void toImagePreview(ArrayList<PreviewBean> previewBeans, int postion) {
        ARouter.getInstance().build(RouterUrl.PATH_MODULE_WELFARE_IMAGE_PREVIEW)
                .withParcelableArrayList(RouterIntentKV.KEY_WELFARE_PHOTO, previewBeans)
                .withInt(RouterIntentKV.KEY_WELFARE_POSITION, postion)
                .navigation();
    }
}
