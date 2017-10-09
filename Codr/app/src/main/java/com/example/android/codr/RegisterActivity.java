package com.example.android.codr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerUser(View v) {
        // Validate the input

        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.email)).getText().toString();

        // username lengh is > 5 characters
        if (username.length() < 5) {
            Toast.makeText(this, "Username is not long enough", Toast.LENGTH_SHORT).show();
            return; // stop here
        }
        // passwords match
        if (password.equals(confirmPassword) == false) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return; // stop here
        }

        // passwords are at >= 8 characters
        if (password.length() < 8) {
            Toast.makeText(this, "Password is not long enough", Toast.LENGTH_SHORT).show();
            return; // stop here
        }

        if (email.length() == 0) {
            Toast.makeText(this, "You're missing an email", Toast.LENGTH_SHORT).show();
            return; // stop here
        }

        // Email has the @ symbol in it
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                break;
            }
            if ((i + 1) == email.length()) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
                return; // stop the function here
            }
        }

        // Save the user, this == activityContext
        DBHelper db = new DBHelper(this);
        db.insertUser(username, password);

        finish(); // complete the current activity
    }

    public void cancelRegistration (View v) {
        finish();
    }
}
