package com.example.android.codr;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CurrentPosts {
    public static List<Post> posts = new ArrayList<>();

    public static void addPost(String author, String content, int authorId) {
        CurrentPosts.posts.add(new Post(author, content, authorId));

        Log.i("CurrentPosts", "Added new post from author:" + author);
        Log.i("CurrentPosts", "Added new post content:" + content);
    }
}
