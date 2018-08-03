package com.example.component_base.Utils;

import android.view.Gravity;

import com.example.component_base.R;

/**
 * Created by wangxiong on 2018/7/30.
 */

public class AnimationUtil {
    private static final int INVALID = -1;
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity ) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
            case Gravity.CENTER:
                return isInAnimation ? R.anim.fade_in_center : R.anim.fade_out_center;
        }
        return INVALID;
    }
}
