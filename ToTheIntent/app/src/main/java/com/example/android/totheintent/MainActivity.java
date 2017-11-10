package com.example.android.totheintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView selfieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selfieImageView = (ImageView)findViewById(R.id.selfieImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap pic = (Bitmap)extras.get("data");
            selfieImageView.setImageBitmap(pic);
        }
    }

    public void onHurtClick(View view) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:7164444444"));
        startActivity(phoneIntent);
    }



    public void onSelfieClick(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 123); // where 123 is a custom request code
    }



    public void onHelpClick(View view) {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setData(Uri.parse(
            "mailto:raghunat@fredonia.edu?subject=Help Me Please!&body=heyyyyy"
        ));
        startActivity(mailIntent);
    }
}



