package com.example.module_common_ui.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.module_common_ui.R;

/**
 * Created by wangxiong on 2018/8/8.
 */

public class MyDialogFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//        //设置主题
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_view_theme);
//
//        //自定义View
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_ui_dialog_view, null);
//        builder.setView(view);
//        Dialog dialog = builder.create();
//
//        /*Dialog dialog = builder.setTitle("标题")
//                .setMessage("setMessage")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定", null)
//                .setCancelable(false)
//                .create();
//*/
//
//        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.wm_popup_bottom_in_animation);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.BOTTOM;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_ui_dialog_view, null);
        /**
         * 不传入theme，宽度无法达到屏幕宽度
         */
        //Dialog dialog = new Dialog(this);
        Dialog dialog = new Dialog(getContext(), R.style.dialog_view_theme);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        dialog.getWindow().setWindowAnimations(R.style.wm_popup_bottom_in_animation);

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        return dialog;
    }
}


