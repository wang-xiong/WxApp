package com.example.app_mvvm.test3.repository;

import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test4.config.URL;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Call<Project> getProjectDetails(@Path("user") String user, @Path("reponame") String projectName);

    @GET(URL.PROJECT_LIST)
    Flowable<List<Project>> loadProjectList(@Path("user") String user);

}
