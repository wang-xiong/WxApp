package com.example.module_common_ui.view.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.Button;

import com.example.module_common_ui.R;
import com.example.module_common_ui.view.customview.MyView;

/**
 * Created by wangxiong on 2018/8/23.
 */

public class MyAnimation {
    private Context mContext;

//    ValueAnimator.oFloat（）采用默认的浮点型估值器 (FloatEvaluator)
//    ValueAnimator.ofInt（）采用默认的整型估值器（IntEvaluator）
//    ValueAnimator.ofObject（）无默认估值器，需要自己定义

    //代码设置
    private void valueAnimator() {
        final Button mButton = null;//= (Button) findViewById(R.id.Button);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mButton.getLayoutParams().width, 500);
        valueAnimator.setDuration(2000);
        valueAnimator.setStartDelay(1000);
        valueAnimator.setRepeatCount(1); //动画重复播放次数=count+1;
        valueAnimator.setRepeatMode(ValueAnimator.RESTART); //正序播放
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE); //倒序回放
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                mButton.getLayoutParams().width = currentValue;
                mButton.requestLayout();
            }
        });
        valueAnimator.start();

        Object object1 = null;
        Object object2 = null;
        ValueAnimator objectAnimator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                return null;
            }
        },object1, object2);
    }

    //xml配置
    private void valueAnimator1() {
        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.value_animator);
        animator.setTarget(new MyView(mContext));
        animator.start();
    }


    private void objectAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(new MyPointView(mContext), "alpha", 1f, 0f ,1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(new MyPointView(mContext), "rotation", 0, 360);
        MyPointView pointView = new MyPointView(mContext);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(new MyPointView(mContext), "translationX", pointView.getTranslationX(), 300, pointView.getTranslationX());
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(new MyPointView(mContext), "scaleX", 1f, 3f, 1f);

        animator.setDuration(2000);
        animator.start();
    }

    private void objectAnimator1() {
        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.object_animator);
        animator.setTarget(new MyPointView(mContext));
        animator.start();
    }

}
