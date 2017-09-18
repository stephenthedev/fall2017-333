package com.example.android.codr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerUser(View v) {
        // Save the user
        // TODO
        finish(); // complete the current activity
    }

    public void cancelRegistration (View v) {
        finish();
    }
}
