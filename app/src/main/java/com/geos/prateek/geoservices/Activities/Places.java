package com.geos.prateek.geoservices.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.geos.prateek.geoservices.Data.LocationRecycleViewAdapter;
import com.geos.prateek.geoservices.Model.Location;
import com.geos.prateek.geoservices.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Places extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationRecycleViewAdapter locationRecycleViewAdapter;
    private List<Location> locationList;
    private RequestQueue queue;
    private TextView name;
    private TextView distance;
    private TextView address;
    private String jsonResponse;
    private String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Intent intent = getIntent();
        resp = intent.getStringExtra("ress");
        Log.d("recieved",resp);
        recyclerView = (RecyclerView)  findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(Places.this));
        locationList = new ArrayList<>();
        locationList = getLocation(resp);

        locationRecycleViewAdapter = new LocationRecycleViewAdapter(this,locationList);
        recyclerView.setAdapter(locationRecycleViewAdapter);
        locationRecycleViewAdapter.notifyDataSetChanged();

//        getLocation(resp);

//        try {
//            JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("ress"));
//            JSONArray locationList = jsonObj.getJSONArray("location");
//            for(int i=0; i<locationList.length(); i++){
//
//                Log.d("abc",locationList.getJSONObject(i).getJSONObject("poi").getString("alias") + " ");
//                Log.d("cde",locationList.getJSONObject(i).getJSONObject("distance").getString("value"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
    public List<Location> getLocation(final String search){
        locationList.clear();
        try {
            JSONObject jsonObj = new JSONObject(search);
            JSONArray locList = jsonObj.getJSONArray("location");
            for(int i=0; i<locList.length(); i++){
                Location location = new Location();
                location.setName("Name : " + locList.getJSONObject(i).getJSONObject("poi").getString("alias") + " ");

                location.setDistance("Distance :" + locList.getJSONObject(i).getJSONObject("distance").getString("value") + " Feet");
                location.setAddress("Address : " + locList.getJSONObject(i).getJSONObject("poi").getJSONObject("contactDetails").getJSONObject("address").getString("formattedAddress"));
//                Log.d("abc",locList.getJSONObject(i).getJSONObject("poi").getString("alias") + " ");
//                Log.d("cde",locList.getJSONObject(i).getJSONObject("distance").getString("value"));
                locationList.add(location);
            }
//            locationRecycleViewAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locationList;
//    }
}
}
