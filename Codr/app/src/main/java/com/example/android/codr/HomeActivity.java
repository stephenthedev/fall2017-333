package com.example.android.codr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toast.makeText(this, "Welcome " + CurrentUser.name, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the current list of posts, and for each one,
        // Add a Linear layout to the allPosts LL.

        LinearLayout allPosts = (LinearLayout)findViewById(R.id.allPosts);

        for (int i = 0; i < CurrentPosts.posts.size(); i++) {
            Post currentPost = CurrentPosts.posts.get(i);

            // Create the post views
            LinearLayout postLayout = new LinearLayout(this);
            postLayout.setOrientation(LinearLayout.VERTICAL);
            TextView author = new TextView(this);
            TextView content = new TextView(this);
            author.setText(currentPost.author);
            content.setText(currentPost.content);

            // Add the text views to the post layout
            postLayout.addView(content);
            postLayout.addView(author);

            // Add the post layout to the allPosts linear layout
            allPosts.addView(postLayout);
        }
    }

    public void newPost(View v) {
        Toast.makeText(this, "Starting new post", Toast.LENGTH_SHORT).show();
        Intent newPost = new Intent(this, NewPostActivity.class);
        startActivity(newPost);
    }
}
