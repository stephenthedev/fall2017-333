package com.example.android.webapiexample;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPostTask extends AsyncTask<Void, Void, String> {

    public String content;
    public TextView textView;
    public ProgressBar loader;

    @Override
    protected String doInBackground(Void... params) {
        SystemClock.sleep(5000);

        // Use a try catch to catch internet errors
        try {
            URL url = new URL("http://jsonplaceholder.typicode.com/posts/43");

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );

            StringBuilder builder = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            reader.close();

            content = builder.toString();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(content);
        textView.setVisibility(View.VISIBLE);
        loader.setVisibility(View.INVISIBLE);

        try {
            JSONObject post = (JSONObject)new JSONTokener(content).nextValue();

            String title = post.getString("title");
            String content = post.getString("content");
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}
