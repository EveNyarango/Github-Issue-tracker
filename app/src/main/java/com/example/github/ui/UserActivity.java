package com.example.github.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;


import com.example.github.Constant;
import com.example.github.R;
import com.example.github.adapter.UserAdapter;
import com.example.github.models.Item;
import com.example.github.models.UserResponse;
import com.example.github.network.GithubApi;
import com.example.github.network.GithubClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.github.Constant.GITHUB_API_TOKEN;


public class UserActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.userRecyclerView)
    RecyclerView mRecyclerView;
    private UserAdapter userAdapter;
    private List<Item> mUsersList;
    private static final String TAG = UserActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        String userName = getIntent().getStringExtra("userName");
        fetchUsers(userName);
    }

    public String parseErrorResponse(Response<UserResponse> response){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(response.errorBody()).byteStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    public void fetchUsers(String userName){
        GithubApi client = GithubClient.getClient();
        Call<UserResponse> call = client.getUsers(userName, GITHUB_API_TOKEN);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mUsersList = response.body().getItems();
                    userAdapter = new UserAdapter(mUsersList);
                    Log.i(TAG, "SUCCESSFUL RESPONSE " + mUsersList.get(0).getLogin());
                    mRecyclerView.setAdapter(userAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(UserActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setVisibility(View.VISIBLE);

                } else {
                    Log.e(TAG, "Unsuccessful: " + parseErrorResponse(response));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t );
            }
        });
    }
}