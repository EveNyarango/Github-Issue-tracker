package com.example.github.network;

import com.example.github.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {
    private static Retrofit retrofit = null;

    public static GithubApi urlRequest() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constant.Github_Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GithubApi.class);
    }
}
