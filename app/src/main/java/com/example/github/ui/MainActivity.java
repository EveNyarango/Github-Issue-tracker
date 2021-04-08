package com.example.github.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.github.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.github.Constant.PREFERENCES_SEARCHTERM_KEY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_username)
    EditText mUserNameEditText;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_search)
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mSubmit){
            String userName = mUserNameEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            addToSharedPreferences(userName);
            intent.putExtra(PREFERENCES_SEARCHTERM_KEY, userName);
            Toast.makeText(MainActivity.this, userName, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
    private void addToSharedPreferences(String userName) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(PREFERENCES_SEARCHTERM_KEY, userName).apply();
    }
}