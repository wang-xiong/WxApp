package com.example.app_mvvm.test3.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_mvvm.R;
import com.example.app_mvvm.databinding.ProjectListItemBinding;
import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.view.callback.ProjectClickCallback;

import java.util.List;
import java.util.Objects;

public class ProjectAdapter1 extends RecyclerView.Adapter<ProjectAdapter1.ProjectViewHolder>{

    private List<Project> projectList;
    private final ProjectClickCallback projectClickCallback;

    public ProjectAdapter1(ProjectClickCallback projectClickCallback) {
        this.projectClickCallback = projectClickCallback;
    }

    public void setProjectList(final List<Project> projectList) {
        if (this.projectList == null) {
            this.projectList = projectList;
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ProjectAdapter1.this.projectList.size();
                }

                @Override
                public int getNewListSize() {
                    return projectList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ProjectAdapter1.this.projectList.get(oldItemPosition).id ==
                            projectList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int newItemPosition, int oldItemPosition) {
                    Project project = projectList.get(newItemPosition);
                    Project old = projectList.get(oldItemPosition);
                    return project.id == old.id
                            && Objects.equals(project.git_url, old.git_url);
                }
            });
            this.projectList = projectList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //下边这个会显示不全有问题
        //View itemView = View.inflate(viewGroup.getContext(), R.layout.project_list_item1, null);
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_list_item1, viewGroup, false);

        return new ProjectViewHolder(itemView);
//        ProjectListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
//                , R.layout.project_list_item, viewGroup, false);
//        binding.setCallback(projectClickCallback);
//        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
//        holder.binding.setProject(projectList.get(position));
//        holder.binding.executePendingBindings();
        final Project project = projectList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectClickCallback.onClick(project);
            }
        });
        holder.name.setText(project.name);
        holder.languages.setText(String.format(holder.itemView.getContext().getString(R.string.languages), project.language));
        holder.project_watchers.setText(String.format(holder.itemView.getContext().getString(R.string.watchers), String.valueOf(project.watchers)));
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView name;
        private TextView languages;
        private TextView project_watchers;

        //final ProjectListItemBinding binding;

//        public ProjectViewHolder(ProjectListItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }

        public ProjectViewHolder(View view) {
            super(view);
            this.itemView = view;
            name = view.findViewById(R.id.name);
            languages = view.findViewById(R.id.languages);
            project_watchers = view.findViewById(R.id.project_watchers);
        }
    }
}
