package com.example.android.codr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewPostActivity extends AppCompatActivity {

    private boolean editMode = false;
    private int postId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // check to see if we are updating one, or creating one
        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("content") != null) {
            // fill in the edit text
            EditText contentText = (EditText)findViewById(R.id.contentText);
            contentText.setText(bundle.getString("content"));

            // put this activity into edit mode
            editMode = true;
            postId = bundle.getInt("postId");
        }
    }

    public void cancelPost(View v) {
        finish();
    }

    public void savePost(View v) {

        EditText contentText = (EditText)findViewById(R.id.contentText);
        String content = contentText.getText().toString();



        // save the post to the DB
        DBHelper db = new DBHelper(this);

        if (editMode) {
            db.updatePost(postId, content);
            CurrentPosts.updatePost(postId, content);
        } else {
            CurrentPosts.addPost(CurrentUser.name, content, CurrentUser.id);
            db.insertPost(content, CurrentUser.id);
        }

        finish();
    }
}
