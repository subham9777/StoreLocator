package com.geos.prateek.geoservices;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import pb.ApiClient;
import pb.ApiException;
import pb.Configuration;
import pb.locationintelligence.LIAPIGeoEnrichServiceApi;
import pb.locationintelligence.model.Locations;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView tv;
    private Button bttn;
    private Button lauch;
    private LocationManager locMgr;
    private String[] loc = new String[2];
    private String output;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        bttn = findViewById(R.id.button);
        lauch = findViewById(R.id.button2);

        locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        } else {
            bttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configure_button();
                }
            });
            lauch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyAsyncTask().execute();
                    tv.setText("Output: " + output);
                }
            });
        }
    }

    void configure_button() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        if (loc[0] == null) {
            tv.setText("Waiting for Location..!!\nClick Again");
        } else {
            tv.setText("Lat: " + loc[0] + " Long: " + loc[1]);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        loc[0] = String.valueOf(location.getLatitude());
        loc[1] = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setoAuthApiKey("kMzuQFXqXoMuO1OMeAjQJzJAjibWbmas");
            defaultClient.setoAuthSecret("Yfr6ZCKAAjxuHIED");

            final LIAPIGeoEnrichServiceApi api = new LIAPIGeoEnrichServiceApi();

            String searchRadius = "2640";
            String searchRadiusUnit = "feet";

            Locations resp = null;

            try {
                Log.i("GeoAPIs","getAddress");
                resp = api.getAddress(loc[0], loc[1], searchRadius, searchRadiusUnit);
                Log.d("Resp", resp.toString());
                gson = new Gson();
                output = gson.toJson(resp);
                Log.d("Out", output);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            return resp.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Result",result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }
}
