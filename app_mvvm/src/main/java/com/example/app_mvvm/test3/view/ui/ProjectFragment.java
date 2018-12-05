package com.example.app_mvvm.test3.view.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ProjectFragment extends Fragment {
    private static final String KEY_PROJECT_ID = "project_id";
    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }
}
