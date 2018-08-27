package com.example.module_common_ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.module_common_ui.widget.MyDialogFragment;
import com.example.module_common_ui.widget.MyDialogFragment2;

/**
 * Created by wangxiong on 2018/8/8.
 */

public class DialogActivity extends BaseMvcActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_dialog);
        findViewById(R.id.first_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog1();
            }
        });
        findViewById(R.id.first_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog2();
            }
        });
        findViewById(R.id.first_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog3();
            }
        });
    }

    private void showMyDialog1() {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    private void showMyDialog2() {
        MyDialogFragment2 dialogFragment = new MyDialogFragment2();
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment2");
    }


    private void showDialog3() {
        View view = LayoutInflater.from(this).inflate(R.layout.common_ui_dialog_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog_view_theme);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setWindowAnimations(R.style.wm_popup_bottom_in_animation);
        dialog.show();

        //要在show之后改变高度，否则无效



        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);


    }
}
