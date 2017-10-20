package com.example.android.codr;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements PostFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get the text view, and change the text
        TextView username = (TextView)findViewById(R.id.username);

        username.setText(CurrentUser.name);

        // Get all posts for this user
        DBHelper db = new DBHelper(this);
        List<Post> posts = db.getPostsByUserId(CurrentUser.id);

        // 1. Get the linear layout that holds posts
        // 2. Get the fragment manager
        // 3. Create a PostFragement for each post
        // 4. Add each fragment to the linear layout
        // 5. Tell the fragment manager you are done

        LinearLayout myPosts = (LinearLayout)findViewById(R.id.myPosts);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        for (int i = 0; i < posts.size(); i++) {
            Post currentPost = posts.get(i);
            transaction.add(
                myPosts.getId(),
                PostFragment.newInstance(
                        currentPost.author,
                        currentPost.content,
                        currentPost.authorId
                )
            );
        }

        transaction.commit();
    }


    public void editPost(View v, String content, int postId) {
        Intent intent = new Intent(this, NewPostActivity.class);

        intent.putExtra("content", content);
        intent.putExtra("postId", postId);

        startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
