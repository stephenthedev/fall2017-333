package com.example.android.codr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
    }

    public void cancelPost(View v) {
        finish();
    }

    public void savePost(View v) {

        EditText contentText = (EditText)findViewById(R.id.contentText);
        String content = contentText.getText().toString();

        CurrentPosts.addPost(CurrentUser.name, content);


        finish();
    }
}
