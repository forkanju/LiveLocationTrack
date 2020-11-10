package com.forkan.livelocationtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocationActivity extends AppCompatActivity {
    //Initialize variable
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        //Initialize variable
        btLocation = (Button) findViewById(R.id.bt_current_location);
        textView1 = (TextView) findViewById(R.id.text_view1);
        textView2 = (TextView) findViewById(R.id.text_view2);
        textView3 = (TextView) findViewById(R.id.text_view3);
        textView4 = (TextView) findViewById(R.id.text_view4);
        textView5 = (TextView) findViewById(R.id.text_view5);

        //Initialize fusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission granted
                if (ActivityCompat.checkSelfPermission(CurrentLocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    getCurrentLocation();

                } else {
                    ActivityCompat.requestPermissions(CurrentLocationActivity.this,
                            new String[]{Manifest.permission
                                    .ACCESS_FINE_LOCATION}, 100);
                }

               // getCurrentLocation();

            }
        });
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            fusedLocationClient.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            //Initialize location
                            Location location = task.getResult();
                            if (location != null) {
                                try {
                                    //Initialize geoCoder
                                    Geocoder geocoder = new Geocoder(CurrentLocationActivity.this,
                                            Locale.getDefault());
                                    //Initialize address list

                                    List<Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1
                                    );
                                    //Set latitude on TextView
                                    textView1.setText(Html.fromHtml(
                                            "<font color= '#6200EE'><b>Latitude :</b><br></font>"
                                                    + addresses.get(0).getLatitude()
                                    ));
                                    //Set longitude on TextView
                                    textView2.setText(Html.fromHtml(
                                            "<font color= '#6200EE'><b>Longitude :</b><br></font>"
                                                    + addresses.get(0).getLongitude()
                                    ));
                                    //Set country name
                                    textView3.setText(Html.fromHtml(
                                            "<font color= '#6200EE'><b>Country Name :</b><br></font>"
                                                    + addresses.get(0).getCountryName()
                                    ));
                                    //Set Locality
                                    textView4.setText(Html.fromHtml(
                                            "<font color= '#6200EE'><b>Locality :</b><br></font>"
                                                    + addresses.get(0).getLocality()
                                    ));
                                    //Set address
                                    textView4.setText(Html.fromHtml(
                                            "<font color= '#6200EE'><b>Address :</b><br></font>"
                                                    + addresses.get(0).getLocality()
                                    ));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );
        }
    }
}