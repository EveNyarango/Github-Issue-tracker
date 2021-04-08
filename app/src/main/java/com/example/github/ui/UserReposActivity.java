package com.example.github.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.github.R;
import com.example.github.adapter.RepoAdapter;
import com.example.github.models.Repository;
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
import static com.example.github.Constant.PREFERENCES_USERNAME_KEY;


public class UserReposActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.repoRecyclerView) RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.repoSearchView)
    SearchView mSearchView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.noReposTextView)
    TextView mNoReposTextView;

    private List<Repository> mUserRepos;

    private RepoAdapter repoAdapter;

    private static final String TAG = UserReposActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repos);
        ButterKnife.bind(this);

        String userName = getIntent().getStringExtra(PREFERENCES_USERNAME_KEY);
        if(userName == null){
            SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            userName = mSharedPreferences.getString(PREFERENCES_USERNAME_KEY, null);
        }
        fetchUserRepositories(userName);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                repoAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void fetchUserRepositories(String userName){
        GithubApi client = GithubClient.getClient();
        Call<List<Repository>> call = client.getUserRepos(userName,GITHUB_API_TOKEN);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mUserRepos = response.body();
                    repoAdapter = new RepoAdapter(mUserRepos);
                    Log.i(TAG, "SUCCESSFUL RESPONSE " + mUserRepos.size());
                    mRecyclerView.setAdapter(repoAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(UserReposActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    if(mUserRepos.size() == 0){
                        mNoReposTextView.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e(TAG, "Unsuccessful: " + parseErrorResponse(response));
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }

    public String parseErrorResponse(Response<List<Repository>> response){
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.actionSearch);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                repoAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }

}