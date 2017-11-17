package com.example.android.fileapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    TextView externalListTextView;
    ToggleButton isInternalButton;
    EditText newContentEditText;
    EditText newTitleEditText;
    LinearLayout fileListLayout;
    LinearLayout externalFileListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isInternalButton = (ToggleButton)findViewById(R.id.internalToggle);
        newContentEditText = (EditText)findViewById(R.id.newContent);
        newTitleEditText = (EditText)findViewById(R.id.newTitle);
        fileListLayout = (LinearLayout)findViewById(R.id.fileList);
        externalFileListLayout = (LinearLayout)findViewById(R.id.externalFileList);

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
        // Check if external file directory is mounted and writable
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // no external drive detected
            Toast.makeText(this, "Media not mounted", Toast.LENGTH_SHORT).show();
            return; // stop here
        }

        // Get the location of the external app storage
        File externalDirectory = getExternalMediaDirs()[0];

        // Write the file there
        // TODO write the File just like the other File Classess
        File newFile = new File(externalDirectory, fileName);

        try {
            newFile.createNewFile();
            FileOutputStream output = new FileOutputStream(newFile);
            OutputStreamWriter writer = new OutputStreamWriter(output);

            writer.append(fileContent);
            writer.close();

            output.flush();
            output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        // refresh the view
        updateListOfExternalFiles();
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
        // Update the list of internal files
        // NOTE: "File" represents both files and folders in android
        File externalDir = getExternalMediaDirs()[0];
        String newList = "List is: \n";
        File[] files = externalDir.listFiles();

        final Context context = this;

        externalFileListLayout.removeAllViews();

        for (int i = 0; i < files.length; i ++) {
            TextView fileTextView = new TextView(this);
            fileTextView.setText(files[i].getName());
            fileTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File externalDir = getExternalMediaDirs()[0];
                    final TextView tv = (TextView)v;
                    // using the intenrnal path and the textview's text, get the file
                    // reference
                    File file = new File(externalDir, tv.getText().toString());

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
            externalFileListLayout.addView(fileTextView);
        }
    }

    public void deleteFile(String fileName, boolean isInternal) {
        // delete the file by name/placement
    }

    public void onCreateClick(View view) {
        if (isInternalButton.isChecked()) {
            createInternalFile(
                newTitleEditText.getText().toString(),
                newContentEditText.getText().toString()
            );
        } else {
            createExternalFile(
                newTitleEditText.getText().toString(),
                newContentEditText.getText().toString()
            );
        }
    }
}
