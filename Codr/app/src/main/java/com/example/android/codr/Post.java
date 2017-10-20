package com.example.android.codr;

public class Post {
    public String content = "";
    public String author = "UNKOWN";
    public boolean isAdded = false;
    public int authorId = 0;
    public int id = -1;

    public Post(String author, String content, int authorId) {
        this.content = content;
        this.author = author;
        this.authorId = authorId;
    }

    public Post(String author, String content, int authorId, int id) {
        this.content = content;
        this.author = author;
        this.authorId = authorId;
        this.id = id;
    }
}
