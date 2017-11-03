package com.example.android.quizzer;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        final Context context = this;
        final ImageView imageView = (ImageView)findViewById(R.id.imageView);

        // REMOVE: this is an example
        new AlertDialog.Builder(this)
                // We get to customize my alert here:
                .setTitle("My First Alert!")
                .setMessage("Here is my message")
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "YOU WIN!!!", Toast.LENGTH_SHORT).show();

                        // How do we show a green checkmark?
                        // Set the imageview image to a green checkmark
                        imageView.setImageResource(R.drawable.checkmark);
                    }
                })
                .setNegativeButton("No Thanks!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "YOU LOSE!!!", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ex);
                    }
                })
                .setNeutralButton("I am not sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startListDialog();
                    }
                })
                .create() // creates an AlertDialog instance
                .show(); // .show on an instance will show the alert
    }

    public void startListDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Pick my favorite candy:")
                .setItems(R.array.items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "they clicked " + which);
                        // dialog.cancel();
                        // if (which == 3) {
                        //    startMultipleSelect();
                        // }

                        // Get the answers from the static class
                        // Get the string value of what they clicked on
                        // Go through the answer list, and see if what
                        // They clicked on exists, if so, show the next dialog
                        String chosenAnswer = getResources().getStringArray(R.array.items)[which];

                        for (int i = 0; i < Game.favoriteCandyAnswers.size(); i++) {
                            if (Game.favoriteCandyAnswers.get(i).equals(chosenAnswer)) {
                                startMultipleSelect();
                                return; // Stop the function here
                            }
                        }
                        // if we make it here, they got it wrong
                        // showWrongImage()

                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    public void startMultipleSelect() {
        final List<Integer> answers = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Names of famous dogs:")
                .setMultiChoiceItems(R.array.dogs, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(answers.contains(which)) {
                            answers.remove(answers.indexOf(which));
                        } else {
                            answers.add(which);
                        }
                        Log.i("info", "The current set of answers is" + answers.toString());
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // With the answers they've chosen
                        // Grab the string values
                        // Loop through the answer key, and make sure they each
                        // exist in the answer list they gave
                        // also make sure they gave the same number of answers as
                        // the answer key has.
                    }
                })
                .create()
                .show();
    }
}
