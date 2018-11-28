package com.example.app_mvvm;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentAdapter {

    private static final String TAG = "FragmentAdapter";

    @BindingAdapter(value = {"fragments", "curIndex"}, requireAll = false)
    public static void bind(ViewGroup container, List<Fragment> fragmentList, int curIndex) {
        if (!(container.getContext() instanceof FragmentActivity)) {
            throw new IllegalArgumentException(TAG + "context must instanceof FragmentActivity");
        }

        int containerId = container.getId();
        if (containerId == View.NO_ID) {
            throw new IllegalArgumentException(TAG + "container view must has a id!!!");
        }

        FragmentManager fm = ((FragmentActivity) container.getContext()).getSupportFragmentManager();

        if (fragmentList != null && !fragmentList.isEmpty()) {
            boolean isInited = container.getTag(R.id.db_inited) != null;

            if (!isInited) {
                container.setTag(R.id.db_inited, true);
                for (Fragment fragment : fragmentList) {
                    String tag = fragment.toString();
                    if (fm.findFragmentByTag(tag) == null) {
                        fm.beginTransaction().add(containerId, fragment, tag).commitAllowingStateLoss();
                    }
                }
            }

            for (Fragment fragment : fragmentList) {
                fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
            }

            if (curIndex >= 0 && curIndex < fragmentList.size()) {
                fm.beginTransaction().show(fragmentList.get(curIndex)).commitAllowingStateLoss();
            }

        }
    }
}
