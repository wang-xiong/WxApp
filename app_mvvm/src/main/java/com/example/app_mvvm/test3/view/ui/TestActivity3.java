package com.example.app_mvvm.test3.view.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.app_mvvm.R;
import com.example.app_mvvm.test3.view.ui.ProjectFragment;
import com.example.app_mvvm.test3.view.ui.ProjectListFragment;
import com.example.app_mvvm.test3.model.Project;

public class TestActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        if(savedInstanceState == null) {
            ProjectListFragment fragment = new ProjectListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, ProjectListFragment.TAG)
                    .commit();
        }
    }

    public void show(Project project) {
        ProjectFragment projectFragment = ProjectFragment.forProject(project.name);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container,
                        projectFragment, null).commit();
    }
}
