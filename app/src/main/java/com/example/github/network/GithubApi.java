package com.example.github.network;

import com.example.github.Constant;
import com.example.github.models.GithubIssue;
import com.example.github.models.GithubProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("users/{username}" + Constant.GITHUB_API_TOKEN)
    Call<GithubProfile> getProfile(
            @Path("username") String username);

    @GET("issues")
    Call<List<GithubIssue>> getUserIssues(
            @Query("access_token") String apiKey,
            @Query("filter") String filter
    );

}
