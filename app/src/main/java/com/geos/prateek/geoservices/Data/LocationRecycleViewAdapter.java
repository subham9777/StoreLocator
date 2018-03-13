package com.geos.prateek.geoservices.Data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geos.prateek.geoservices.Model.Location;
import com.geos.prateek.geoservices.R;

import java.util.List;

/**
 * Created by Subham on 05-03-2018.
 */

public class LocationRecycleViewAdapter extends RecyclerView.Adapter<LocationRecycleViewAdapter.ViewHolder>{

    private Context context;
    private List<Location> locationList;
    public LocationRecycleViewAdapter(Context context, List<Location> locations) {
        this.context = context;
        locationList = locations;
    }

    @Override
    public LocationRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_places,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(LocationRecycleViewAdapter.ViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.name.setText(location.getName());
        holder.distance.setText(location.getDistance());
        holder.address.setText(location.getAddress());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView distance;
        TextView address;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            name = itemView.findViewById(R.id.placeName);
            distance = itemView.findViewById(R.id.distance);
            address = itemView.findViewById(R.id.address1);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Movie movie = movieList.get(getAdapterPosition());
//
//                    Intent intent = new Intent(context, MovieDetailActivity.class);
//
//                    intent.putExtra("movieabc",movie);
//                    ctx.startActivity(intent);
//                }
//            });
        }
    }
}
