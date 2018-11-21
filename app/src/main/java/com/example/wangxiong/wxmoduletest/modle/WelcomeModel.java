package com.example.wangxiong.wxmoduletest.modle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.example.wangxiong.wxmoduletest.R;
import com.example.wangxiong.wxmoduletest.contract.WelcomeContract;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class WelcomeModel implements WelcomeContract.Model {
    @Override
    public Bitmap getWelcomeImg(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.welcome);
    }
}
