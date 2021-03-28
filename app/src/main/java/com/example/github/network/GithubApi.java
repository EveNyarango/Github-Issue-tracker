package com.example.github.network;

import androidx.cardview.widget.CardView;

import com.example.github.Constant;
import com.example.github.models.GithubIssue;
import com.example.github.models.GithubProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    @GET("users/{username}" + Constant.GITHUB_API_TOKEN)
    Call<GithubProfile> userProfile(
            @Path("username") String username);

    @GET("repos/{owner}/{repo}/issues")
    Call<List<GithubIssue>> getUserIssues(
            @Path("owner") String owner,
            @Path("repo") String repo);
}
//GET /repos/{owner}/{repo}/issues