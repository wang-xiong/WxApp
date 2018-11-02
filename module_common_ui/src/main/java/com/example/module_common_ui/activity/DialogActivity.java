package com.example.module_common_ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.module_common_ui.widget.MyDialogFragment;
import com.example.module_common_ui.widget.MyDialogFragment2;
import com.example.module_common_ui.widget.MyDialogFragment3;

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

        findViewById(R.id.first_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        findViewById(R.id.first_btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFragment3();
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

    private void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog, null);
        TextView content = v.findViewById(R.id.exit_tv);

        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

    }

    private void showDialogFragment3() {
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("MyDialogFragment3");
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        MyDialogFragment3 dialogFragment =  MyDialogFragment3.newInstance("超时赔付权益说明", "星选用户在选择星选App商家下单时，星选用户在选择星选App商家下单时星选用户在选择星选App商家下单时，星选用户在选择星选App商家下单时");
        dialogFragment.show(fragmentTransaction, "MyDialogFragment3");
    }
}
