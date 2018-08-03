package com.example.module_common_ui.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.module_common_ui.widget.CommonPopupWindow;
import com.example.module_common_ui.widget.CustomPopupWindow;

/**
 * Created by wangxiong on 2018/7/25.
 */

public class PopupActivity extends BaseMvcActivity {
    private View mRootView;
    private CommonPopupWindow mCommonPopupWindow;
    private CustomPopupWindow mCustomPopupWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_popup);
        mRootView = findViewById(R.id.root_view);
        Button button1 = findViewById(R.id.bt_onclick1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommonPopup(v);
            }
        });
        Button button2 = findViewById(R.id.bt_onclick2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomPopup(v);
            }
        });
    }

    public void showCommonPopup(View view) {
        if (mCommonPopupWindow != null && mCommonPopupWindow.isShowing()) {
            return;
        }
        mCommonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.common_ui_popup_child_menu)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
                .setOutsideTouchable(true)
                .create();
        mCommonPopupWindow.showAsDropDown(view, -mCommonPopupWindow.getWidth(), -view.getHeight());
    }

    private void showCustomPopup(View view) {
        if (mCustomPopupWindow != null && mCustomPopupWindow.isShowing()) {
            return;
        }
        mCustomPopupWindow = new CustomPopupWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_product_detail_video)
                .setBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .setAnimationStyle(R.style.pop_bottom_anim)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }
}
