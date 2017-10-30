package com.example.android.codr;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements PostFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Populate for the first time, CurrentPosts.posts
        DBHelper db = new DBHelper(this);

        CurrentPosts.posts = db.getAllPosts();
        setContentView(R.layout.activity_home);

        Toast.makeText(this, "Welcome " + CurrentUser.name, Toast.LENGTH_SHORT).show();
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the current list of posts, and for each one,
        // Add a Linear layout to the allPosts LL.

        LinearLayout allPosts = (LinearLayout)findViewById(R.id.allPosts);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        for (int i = 0; i < CurrentPosts.posts.size(); i++) {
            Post currentPost = CurrentPosts.posts.get(i);

            if (currentPost.isAdded) {
                continue;
            }

            transaction.add(
                    allPosts.getId(),
                    PostFragment.newInstance(
                            currentPost.author,
                            currentPost.content,
                            currentPost.authorId,
                            currentPost.id
                    )
            );
        }

        // All fragments added, commit
        transaction.commit();
    }

    public void newPost(View v) {
        Toast.makeText(this, "Starting new post", Toast.LENGTH_SHORT).show();
        Intent newPost = new Intent(this, NewPostActivity.class);
        startActivity(newPost);
    }

    public void openMyProfile(View v) {
        Intent myProfile = new Intent(this, ProfileActivity.class);
        startActivity(myProfile);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
