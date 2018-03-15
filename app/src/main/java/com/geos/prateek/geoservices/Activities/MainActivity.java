package com.geos.prateek.geoservices.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.geos.prateek.geoservices.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pb.ApiClient;
import pb.ApiException;
import pb.Configuration;
import pb.locationintelligence.LIAPIGeoEnrichServiceApi;
import pb.locationintelligence.model.Locations;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView tvLat;
    private TextView tvLong;
    private Button coordBtn;
    private Button getDetails;
    private Spinner categorySp;
    private EditText radIn;
    private EditText time;
    private EditText resultNo;
    private LocationManager locMgr;
    private Gson gson;
    private JSONObject locationOut;
    public String res;
    private String searchRadius;
    private String searchRadiusUnit = "feet";
    private String longitude;
    private String latitude;
    private String brandName = null;
    private String category = null;
    private String maxCandidates = "8";
    private String searchDataset = null;
    private String searchPriority = null;
    private String travelTime = null;
    private String travelTimeUnit = null;
    private String travelDistance = null;
    private String travelDistanceUnit = null;
    private String mode = null;
    private ProgressDialog progressDialog;
    private RequestQueue queue;
//    public List<com.geos.prateek.geoservices.Model.Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLat = findViewById(R.id.textViewLat);
        tvLong = findViewById(R.id.textViewLong);

        coordBtn = findViewById(R.id.button);
        getDetails = findViewById(R.id.button2);
//        queue = Volley.newRequestQueue(this);
//        locationList = new ArrayList<>();
//        searchRadius = (EditText)findViewById(R.id.editText2);
        final boolean grant = checkLocationPermission();

        locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        } else {
            coordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configure_button();
                }
            });
            getDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyAsyncTask().execute();

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
        if (latitude == null) {
            tvLat.setText("Waiting");
            tvLong.setText("Waiting");
        }else {
            tvLat.setText("Lat: " + latitude);
            tvLong.setText("Long: " + longitude);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
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
//        intent.putExtra("jsonResponse",res);
        startActivity(intent);
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setoAuthApiKey("kMzuQFXqXoMuO1OMeAjQJzJAjibWbmas");
            defaultClient.setoAuthSecret("Yfr6ZCKAAjxuHIED");

            final LIAPIGeoEnrichServiceApi api = new LIAPIGeoEnrichServiceApi();

            Locations resp = null;

            try {
//                 Getting values from user
//                category = String.valueOf(categorySp.getSelectedItem());

//                travelTime = String.valueOf(time.getText());
//                maxCandidates = String.valueOf(resultNo.getText());

//                Log.i("GeoAPIs","getAddress");
                resp = api.getEntityByLocation( longitude,  latitude,  brandName,  category,  maxCandidates,  searchRadius,  searchRadiusUnit,
                        searchDataset,  searchPriority,  travelTime,  travelTimeUnit,  travelDistance,  travelDistanceUnit,  mode);
//                Log.d("Resp", resp.toString());
                gson = new Gson();
                locationOut = new JSONObject(gson.toJson(resp));
                Log.d("Out", locationOut.toString());
//                JSONArray locationList = locationOut.getJSONArray("location");
//                for(int i=0; i<locationList.length(); i++){
//
//                    System.out.print(locationList.getJSONObject(i).getJSONObject("poi").getString("alias") + " ");
//                    System.out.println(locationList.getJSONObject(i).getJSONObject("distance").getString("value"));
//                }

                res = locationOut.toString();

            } catch (ApiException | JSONException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
//            Log.i("Result",result);
            Log.i("json",res);
            if (!locationOut.toString().isEmpty()) {
                Intent intent = new Intent(MainActivity.this, com.geos.prateek.geoservices.Activities.Places.class);
                intent.putExtra("ress", String.valueOf(locationOut));
                startActivity(intent);

//                    if (output == null) {
//                        tvLat.setText("Waiting for PitneyBowes...!!");
//                    }else {
//                        tvLat.setText(output);
//                    }
            }
            progressDialog.dismiss();
//            progressDialog.dismiss();
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, result, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//            try {
//                JSONArray locationArray = response.getJSONArray("location");
//                for (int i=0;i<locationArray.length();i++)
//                {
//                    JSONObject locObj = locationArray.getJSONObject(i);
//                    com.geos.prateek.geoservices.Model.Location loc = new com.geos.prateek.geoservices.Model.Location();
//                    if (locObj.has("distance")){
//                        JSONArray dist = response.getJSONArray("distance");
//                        String value = null;
//                        JSONObject distValue = dist.getJSONObject(dist.length());
//                        value = (distValue.getString("value"));
////                        distance.setText("Distance : " + value + " Feet");
//                        Log.d("Distance : " ,value);
//                        loc.setDistance("Distance : " + value + " Feet");
//
//                    }else {
//                        loc.setDistance("N/A");
//                    }
//                    if (locObj.has("poi")){
//                        JSONArray poi = response.getJSONArray("poi");
//                        String alias = null;
//                        JSONObject distValue = poi.getJSONObject(poi.length());
//                        alias = (distValue.getString("alias"));
////                        name.setText("Name : " + alias);
//                    }else {
//                        loc.setName("N/A");
//                    }
//                    locationList.add(loc);
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//            queue.add(jsonObjectRequest);
//
        }



        @Override
        protected void onProgressUpdate(Void... values) {}}

        public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

        public boolean checkLocationPermission() {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    new AlertDialog.Builder(this)
                            .setTitle("Location")
                            .setMessage("Grant Permission")
                            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Prompt the user once explanation has been shown
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }
                            })
                            .create()
                            .show();


                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // location-related task you need to do.
                        if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            //Request location updates:
                            locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
                        }

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                    }
                    return;
                }

            }

    }
}
