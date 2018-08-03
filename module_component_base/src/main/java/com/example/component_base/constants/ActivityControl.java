package com.example.component_base.constants;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class ActivityControl {


    private Set<Activity> mActivitySets = new HashSet<>();


    public void addActivity(Activity activity) {
        mActivitySets.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivitySets != null) {
            mActivitySets.remove(activity);
        }
    }

    public void finishAll() {
        if(mActivitySets!= null) {
            for (Activity activity : mActivitySets) {
                activity.finish();
            }
        }
    }
}
