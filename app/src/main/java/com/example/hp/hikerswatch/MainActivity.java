package com.example.hp.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         final TextView latitude=(TextView)findViewById(R.id.textView2);
         final TextView longitude=(TextView)findViewById(R.id.textView3);
         final TextView accuracy=(TextView)findViewById(R.id.textView4);
         final TextView altitude=(TextView)findViewById(R.id.textView5);
         final TextView add=(TextView)findViewById(R.id.textView6);

        locationManager= (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude.setText("Latitude: "+location.getLatitude());
                longitude.setText("Longitude: "+location.getLongitude());
                accuracy.setText("Accuracy: "+location.getAccuracy());
                altitude.setText("Altitude: "+location.getAltitude());
                Geocoder geocoder=new Geocoder(getApplicationContext(),Locale.getDefault());
                List<Address> listAddress= null;
                try {
                    listAddress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(listAddress!=null && listAddress.size()>0) {
                        String address = "";
                        if (listAddress.get(0).getThoroughfare() != null) {
                            address += listAddress.get(0).getThoroughfare() + ",";
                        }

                        if (listAddress.get(0).getLocality() != null) {
                            address += listAddress.get(0).getLocality() + ",";
                        }
                        if (listAddress.get(0).getPostalCode() != null) {
                            address += listAddress.get(0).getPostalCode() + ",";
                        }
                        if (listAddress.get(0).getAdminArea() != null) {
                            address += listAddress.get(0).getAdminArea();
                        }
                        add.setText("Address: " + address);
                    }
                    else{
                        add.setText("Address: ");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }



}
