package com.geos.prateek.geoservices.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.geos.prateek.geoservices.Data.LocationRecycleViewAdapter;
import com.geos.prateek.geoservices.Model.Location;
import com.geos.prateek.geoservices.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            jsonResponse = bundle.getString("jsonResponse");
//        }
//
//        recyclerView = (RecyclerView)  findViewById(R.id.rec_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        locationList = new ArrayList<>();
//        getLocation(jsonResponse);
//        queue = Volley.newRequestQueue(this);
//        locationRecycleViewAdapter = new LocationRecycleViewAdapter(this,locationList);
//        recyclerView.setAdapter(locationRecycleViewAdapter);
//        locationRecycleViewAdapter.notifyDataSetChanged();
//        setUI();
//
//    }
//
//    private void setUI() {
//        name = findViewById(R.id.placeName);
//        distance = findViewById(R.id.distance);
//        address = findViewById(R.id.address1);
//
//    }
//
//    public List<Location> getLocation(final String search){
//        locationList.clear();
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, search, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Log.d("json",search);
//                try {
//                    JSONArray locationArray = response.getJSONArray("location");
//                    for (int i=0;i<locationArray.length();i++)
//                    {
//                        JSONObject locObj = locationArray.getJSONObject(i);
//                        Location location = new Location();
//                        if (locObj.has("distance")){
//                            JSONArray dist = response.getJSONArray("distance");
//                            String value = null;
//                            JSONObject distValue = dist.getJSONObject(dist.length());
//                            value = (distValue.getString("value"));
//                            distance.setText("Distance : " + value + " Feet");
//                        }else {
//                            location.setDistance("N/A");
//                        }
//                        if (locObj.has("poi")){
//                            JSONArray poi = response.getJSONArray("poi");
//                            String alias = null;
//                            JSONObject distValue = poi.getJSONObject(poi.length());
//                            alias = (distValue.getString("alias"));
//                            name.setText("Name : " + alias);
//                        }else {
//                            location.setName("N/A");
//                        }
//                    }
//                    locationRecycleViewAdapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue.add(jsonObjectRequest);
//        return locationList;
//    }
    }
}
