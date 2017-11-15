package com.example.android.fileapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView externalListTextView;
    ToggleButton isInternalButton;
    EditText newContentEditText;
    EditText newTitleEditText;
    LinearLayout fileListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        externalListTextView = (TextView)findViewById(R.id.externalList);
        isInternalButton = (ToggleButton)findViewById(R.id.internalToggle);
        newContentEditText = (EditText)findViewById(R.id.newContent);
        newTitleEditText = (EditText)findViewById(R.id.newTitle);
        fileListLayout = (LinearLayout)findViewById(R.id.fileList);

        // Populate the text views on create
        updateListOfExternalFiles();
        updateListOfInternalFiles();
    }

    public void createInternalFile(String fileName, String fileContent) {
        // Get the location of the internal app storage
        // Write the file there
        FileOutputStream outputStream ;

        try {
            // Mode Private =  internal app directory
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContent.getBytes());
            outputStream.close();
        } catch(Exception e) {
            Log.e("WRITE ERROR", "Failed to write", e);
        }

        // Update the list
        updateListOfInternalFiles();
    }

    public void createExternalFile(String fileName, String fileContent) {
        // Check if external file directory is mounted
        // Get the location of the external app storage
        // Write the file there
    }

    public void updateListOfInternalFiles() {
        // Update the list of internal files
        // NOTE: "File" represents both files and folders in android
        File internalDir = getFilesDir();
        String newList = "List is: \n";
        File[] files = internalDir.listFiles();

        final Context context = this;

        fileListLayout.removeAllViews();

        for (int i = 0; i < files.length; i ++) {
            TextView fileTextView = new TextView(this);
            fileTextView.setText(files[i].getName());
            fileTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File internalDir = getFilesDir();
                    final TextView tv = (TextView)v;
                    // using the intenrnal path and the textview's text, get the file
                    // reference
                    File file = new File(internalDir, tv.getText().toString());

                    StringBuilder text = new StringBuilder();

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));

                        String line;
                        while ((line = br.readLine()) != null) {
                            text.append(line + "\n");
                        }

                        br.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }

                    new AlertDialog.Builder(context)
                            .setTitle(tv.getText())
                            .setMessage(text.toString())
                            .setPositiveButton("OK", null)
                            .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int option) {
                                    deleteFile(tv.getText().toString());
                                    updateListOfInternalFiles();
                                }
                            })
                            .create()
                            .show();
                }
            });
            fileListLayout.addView(fileTextView);
        }
    }

    public void updateListOfExternalFiles() {
        // Update the list of external files
    }

    public void deleteFile(String fileName, boolean isInternal) {
        // delete the file by name/placement
    }

    public void onCreateClick(View view) {
        // TODO handle the toggle button
        // TODO create a way to specify filename vs file content

        createInternalFile(
            newTitleEditText.getText().toString(),
            newContentEditText.getText().toString()
        );
    }
}
