package com.example.android.codr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "codr.db";

    public DBHelper(Context activityContext) {
        super(activityContext, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT)"
        );
        sqLiteDatabase.execSQL(
            "CREATE TABLE posts ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INT, " +
                "content TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS posts");
        onCreate(sqLiteDatabase);
    }

    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        db.insert("users", null, contentValues);
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM users WHERE username = '" + username + "'", null);

        // if no results, return null
        if(result.isAfterLast()) {
            return null;
        }

        // move the cursor to the first row
        result.moveToFirst();
        String name = result.getString(result.getColumnIndex("username"));
        String password = result.getString(result.getColumnIndex("password"));

        int id = result.getInt(result.getColumnIndex("id"));

        return new User(name, password, id);
    }

    public void insertPost(String content, Integer userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("userId", userId);

        db.insert("posts", null, contentValues);
    }

    public void updatePost(int postId, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        db.update("posts", contentValues, "id=" + Integer.toString(postId), null);
    }

    public List<Post> getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery(
            "SELECT posts.id, username, content, posts.userId FROM posts " +
            "LEFT JOIN users on users.id = posts.userId " +
            "ORDER BY posts.id DESC",
            null
        );

        // loop and create all posts
        List<Post> posts = new ArrayList<Post>();

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            posts.add(new Post(
                res.getString(res.getColumnIndex("username")),
                res.getString(res.getColumnIndex("content")),
                res.getInt(res.getColumnIndex("userId"))
            ));

            res.moveToNext();
        }

        return posts;
    }

    public List<Post> getPostsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery(
                "SELECT posts.id, username, content FROM posts " +
                        "LEFT JOIN users on users.id = posts.userId " +
                        "WHERE posts.userId = " + userId + " " +
                        "ORDER BY posts.id DESC",
                null
        );

        // loop and create all posts
        List<Post> posts = new ArrayList<Post>();

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            posts.add(new Post(
                    res.getString(res.getColumnIndex("username")),
                    res.getString(res.getColumnIndex("content")),
                    userId
            ));

            res.moveToNext();
        }

        return posts;
    }
}

