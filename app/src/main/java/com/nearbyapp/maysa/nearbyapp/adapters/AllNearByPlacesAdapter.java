package com.nearbyapp.maysa.nearbyapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nearbyapp.maysa.nearbyapp.R;
import com.nearbyapp.maysa.nearbyapp.datamodels.Venue;
import com.squareup.picasso.Picasso;
import java.util.List;


public class AllNearByPlacesAdapter extends
        RecyclerView.Adapter<AllNearByPlacesAdapter.MyViewHolder> {

    private List<Venue> venueList;

    public AllNearByPlacesAdapter(List<Venue> venues ) {
        this.venueList = venues;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Venue venue = venueList.get(position);
        StringBuilder single_address = new StringBuilder();
        for (String s : venue.getLocation().getFormattedAddress()) {
            single_address.append(s).append("\t");
        }
        holder.place_address.setText(String.valueOf(single_address));
        holder.place_name.setText(String.valueOf(venue.getName()));
         if (!venue.getPhoto().equals("no_photo")) { Picasso.get()
                   .load(venue.getPhoto())
                   .into(holder.place_photo);
           }
         else {
           holder.place_photo.setImageResource(R.drawable.place_holder);
         }

    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView place_photo ;
        private TextView place_name , place_address;
        MyViewHolder(View view) {
            super(view);
            place_photo = view.findViewById(R.id.place_photo);
            place_name= view.findViewById(R.id.place_name);
                    place_address= view.findViewById(R.id.place_address);
        }
    }

}
