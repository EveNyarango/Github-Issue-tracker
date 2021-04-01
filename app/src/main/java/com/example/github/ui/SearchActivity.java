package com.example.github.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.github.R;
import com.example.github.models.GithubProfile;
import com.example.github.network.GithubApi;
import com.example.github.network.GithubClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText mUsername;
    @BindView(R.id.btn_search)
    Button mSearch;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        loadingScreen();

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUsername();

            }

        });
    }


    private void searchUsername() {
        String username = mUsername.getText().toString().trim();

        if (!isUsernameValid(username)){
            return;
        }
        progressDialog.show();
        GithubApi client = GithubClient.getClient();
        Call<GithubProfile> call = client.getProfile(username);
        call.enqueue(new Callback<GithubProfile>() {
            @Override
            public void onResponse(Call<GithubProfile> call, Response<GithubProfile> response) {
                if (response.isSuccessful()) {
                    response.body();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(SearchActivity.this, "User not Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GithubProfile> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SearchActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean isUsernameValid(String username) {
        if(username.equals("")){
            mUsername.setError("Enter a valid Username");
            return false;
        }
        return true;
    }

    private void loadingScreen() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Looking for user");
        progressDialog.setMessage("Searching for the user");
        progressDialog.setCancelable(false);
    }
}