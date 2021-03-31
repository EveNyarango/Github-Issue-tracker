package com.example.github;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.github.adapter.IssueAdapter;
import com.example.github.models.GithubIssue;
import com.example.github.network.GithubApi;
import com.example.github.network.GithubClient;
import com.example.github.ui.SplashActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.svUser)
    SearchView mSearchView;
    @BindView(R.id.spinner1) Spinner mSpinner;
    @BindView(R.id.rvIssues)
    RecyclerView recyclerView;
    private IssueAdapter issueAdapter;
    private List<GithubIssue> mIssuesList;
    private ProgressDialog progressDialog;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        loadingScreen();
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mIssuesList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        issueAdapter = new IssueAdapter(mIssuesList);
        recyclerView.setAdapter(issueAdapter);
        GithubApi client = GithubClient.getClient();
        Call<List<GithubIssue>> call = client.getUserIssues("204b78f29b02a15221e2183fc88f08bc60f3b69e","all");
        call.enqueue(new Callback<List<GithubIssue>>() {
            @Override
            public void onResponse(Call<List<GithubIssue>> call, Response<List<GithubIssue>> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "SUCCESSFUL RESPONSE");
                    mIssuesList = response.body();

                    issueAdapter = new IssueAdapter(mIssuesList);
                    recyclerView.setAdapter(issueAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);

                } else {
                    Log.e(TAG, "Unsuccessful: " + parseErrorResponse(response));
                    //showUnsuccessfulMessage();
                }
            }
            @Override
            public void onFailure(Call<List<GithubIssue>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
                //hideProgressBar();
                //showFailureMessage();
            }
        });

    }
    //    private void loadingScreen() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Looking for User");
//        progressDialog.setMessage("Searching for user with the given username");
//        progressDialog.setCancelable(false);
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public String parseErrorResponse(Response<List<GithubIssue>> response){
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String finallyError = sb.toString();
        return finallyError;

    }
}