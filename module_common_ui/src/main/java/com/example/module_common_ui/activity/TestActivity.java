package com.example.module_common_ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.module_common_ui.view.textview.TextViewUtils;

public class TestActivity extends BaseMvcActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_test);
        final TextView textView = findViewById(R.id.test_tv);


        final TextView name1 = findViewById(R.id.name1);
        final TextView name2 = findViewById(R.id.name2);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0.0f);
        long duration = 1000;
        //name2.setMaxLines(1);
        valueAnimator.setDuration(duration);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                name1.setVisibility(View.VISIBLE);
                name2.setVisibility(View.VISIBLE);
                name2.setHeight(97);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                name1.setVisibility(View.GONE);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (Float) animation.getAnimatedValue();
                //name1.setAlpha(alpha);
                name2.setAlpha(1- alpha);
                name2.setHeight((int) (97 + 97 * (1 - alpha)));
            }
        });

        name2.post(new Runnable() {
            @Override
            public void run() {
                int a = name2.getMeasuredHeight();
            }
        });

        findViewById(R.id.test_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                valueAnimator.start();
            }
        });

    }
}
