package com.example.android.webapiexample;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadPost(View v) {
        TextView tv = (TextView)findViewById(R.id.textView);
        ProgressBar loading = (ProgressBar)findViewById(R.id.loadingBar);
        Button button = (Button)findViewById(R.id.getPost);

        button.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

        // Go to the internet, grab the content
        GetPostTask task = new GetPostTask();

        task.textView = tv;
        task.loader = loading;

        // SystemClock.sleep(4000);
        // tv.setText("Loading....");
        // tv.setTextSize(42);

        task.execute();
    }
}
