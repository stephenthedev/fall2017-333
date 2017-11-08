package com.example.android.locationservices;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if  (grantResults.length > 0) {
            // they granted permission, so start listening
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            } catch (SecurityException e) {
                Toast.makeText(this, "No permission granted, restart to try again", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "No permission granted, restart to try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView)findViewById(R.id.textView);

        // Set up a location manager: handles events of location
        // on update of location, update the text view
        // Request updates every 5 seconds
        // Handle location permissions if not enabled

        // Get the existing location manager instance from the device by CONSTANT ID
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        final Context context = this;

        // Listener = the intent that has all the methods for dealing with location
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("LOCATION", "Location changed" + location.toString());
                tv.setText("Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("LOCATION", "Status changed: " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(context, "Provider Enabled: " + provider, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(context, "Provider Disabled: " + provider, Toast.LENGTH_LONG).show();
            }
        };

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        } else {
            Toast.makeText(this, "Permission not granted, asking for permission", Toast.LENGTH_LONG).show();

            // Request Permission
            ActivityCompat.requestPermissions(
                this, // the activity context
                new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET
                },
                2 // A custom integer we can define as the request code
                  // To identify the extra intent this creates (which is the ask for permission)
            );
            // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        }

    }
}
