package com.example.android.codr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void newPost(View v) {
        Toast.makeText(this, "Starting new post", Toast.LENGTH_SHORT).show();
        Intent newPost = new Intent(this, NewPostActivity.class);
        startActivity(newPost);
    }
}
