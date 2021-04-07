package com.example.github.network;

import com.example.github.Constant;
import com.example.github.models.GithubIssue;
import com.example.github.models.GithubProfile;
import com.example.github.models.Repository;
import com.example.github.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("repos/{userName}/{repoName}/issues")
    Call<List<GithubIssue>> getRepoIssues(
            @Path("userName") String userName,
            @Path("repoName") String repoName,
            @Query("access_token") String apiKey,
            @Query("filter") String filter
    );

    @GET("users/{userName}/repos")
    Call<List<Repository>> getUserRepos(
            @Path("userName") String userName,
            @Query("access_token") String apiKey
    );

    @GET("search/users")
    Call<UserResponse> getUsers(
            @Query("q") String searchTerm,
            @Query("access_token") String apiKey
    );

}
