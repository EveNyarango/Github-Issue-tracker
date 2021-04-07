package com.example.github.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.github.R;
import com.example.github.adapter.IssueAdapter;
import com.example.github.models.GithubIssue;
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


public class RepoIssuesActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.issueRecyclerView)
    RecyclerView mRecyclerView;
    private List<GithubIssue> mIssuesList;
    private IssueAdapter issueAdapter;

    private static final String TAG = RepoIssuesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_issues);
        ButterKnife.bind(this);
        String userName = getIntent().getStringExtra("userName");
        String repoName = getIntent().getStringExtra("repoName");
        fetchIssues(userName,repoName);
    }

    public void fetchIssues(String userName, String repoName){
        GithubApi client = GithubClient.getClient();
        Call<List<GithubIssue>> call = client.getRepoIssues(userName,repoName,GITHUB_API_TOKEN,"all");
        call.enqueue(new Callback<List<GithubIssue>>() {
            @Override
            public void onResponse(Call<List<GithubIssue>> call, Response<List<GithubIssue>> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "SUCCESSFUL RESPONSE " + response.body().size());
                    mIssuesList = response.body();
                    issueAdapter = new IssueAdapter(mIssuesList);
                    mRecyclerView.setAdapter(issueAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(RepoIssuesActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setVisibility(View.VISIBLE);

                } else {
                    Log.e(TAG, "Unsuccessful: " + parseErrorResponse(response));
                }
            }
            @Override
            public void onFailure(Call<List<GithubIssue>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }

    public String parseErrorResponse(Response<List<GithubIssue>> response){
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
}