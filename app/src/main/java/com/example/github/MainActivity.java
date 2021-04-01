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
import android.widget.TextView;
import android.widget.Toast;
import com.example.github.adapter.IssueAdapter;
import com.example.github.models.GithubIssue;
import com.example.github.network.GithubApi;
import com.example.github.network.GithubClient;
import com.example.github.ui.SplashActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.tvDate) TextView mDate;


    private IssueAdapter issueAdapter;
    private List<GithubIssue> mIssuesList;
    private ProgressDialog progressDialog;
    Date date;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        Call<List<GithubIssue>> call = client.getUserIssues("access_token","all");
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

            }
        });



            setUpFilterBy();
            setUpDate();


    }

    private void setUpDate() {
        Calendar calendar = Calendar.getInstance();

        date = calendar.getTime();

        String stringDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        mDate.setText(stringDate.replace("2021", ""));
    }

    private void setUpFilterBy() {
        String[] arrayFilterBy = new String[]{"All", "This Week", "This Month", "This Year"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, android.R.id.text1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        for (String filterOptions : arrayFilterBy
        ) {
            adapter.add(filterOptions);

        }
        adapter.notifyDataSetChanged();


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, position, Toast.LENGTH_LONG).show();
                ((TextView) view).setText(null);
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int week = cal.get(Calendar.WEEK_OF_YEAR);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                int dateFromIndex = 0;
                List<GithubIssue> tempRepoIssue = new ArrayList<>();

                if (position > 0) {
                    dateFromIndex = position;
                }

                if (dateFromIndex == 0) {
                    tempRepoIssue = mIssuesList;
                } else {
                    for (GithubIssue title : mIssuesList) {
                        Date d = null;
                        try {
                            d = input.parse(title.getCreatedAt());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (dateFromIndex == 1) {
                            Calendar calIssue = Calendar.getInstance();
                            calIssue.setTime(d);

                            int weekIssue = calIssue.get(Calendar.WEEK_OF_YEAR);
                            if (week == weekIssue) {
                                tempRepoIssue.add(title);
                            }
                        } else if (dateFromIndex == 2) {
                            Calendar calIssue = Calendar.getInstance();
                            calIssue.setTime(d);

                            int monthIssue = calIssue.get(Calendar.MONTH);
                            int yearIssue = calIssue.get(Calendar.YEAR);
                            if (month == monthIssue && year == yearIssue) {
                                tempRepoIssue.add(title);
                            }
                        } else {
                            Calendar calIssue = Calendar.getInstance();
                            calIssue.setTime(d);

                            int yearIssue = calIssue.get(Calendar.YEAR);
                            if (year == yearIssue) {
                                tempRepoIssue.add(title);
                            }
                        }
                    }
                }

                issueAdapter.updateList(tempRepoIssue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


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