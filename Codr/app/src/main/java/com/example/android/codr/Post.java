package com.example.android.codr;

public class Post {
    public String content = "";
    public String author = "UNKOWN";
    public boolean isAdded = false;

    public Post(String author, String content) {
        this.content = content;
        this.author = author;
    }
}
