package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.model.TempleImages;
import com.thenneem.omnitrail.model.TrailMaster;
import com.thenneem.omnitrail.utils.PicassoClient;

import java.util.ArrayList;
import java.util.List;

public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.MyViewHolder> {
    private final Context context;
    private List<TrailMaster> trailMasters = new ArrayList<>();

    public TrailAdapter(Context context, List<TrailMaster> trailMasters) {
        this.context = context;
        this.trailMasters = trailMasters;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_row, parent, false);
        return new TrailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                TrailMaster master = trailMasters.get(position);
                String places = master.getTrailPlaces()+" Places";
                String length = master.getTrailLength()+" KM";
                String days = master.getAvgDays()+" days";

                holder.txtTrail.setText(master.getTrailName());
                holder.txtPlaces.setText(places);
                holder.txtLength.setText(length);
                holder.txtDays.setText(days);
                PicassoClient.downloadImage(context,master.getImage(),holder.imgTrail);
    }

    @Override
    public int getItemCount() {
        return trailMasters.size();
    }

    public void updateList(List<TrailMaster> trails){
        trailMasters = trails;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgTrail;
        private TextView txtTrail, txtPlaces, txtLength, txtDays;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTrail = itemView.findViewById(R.id.imgTrail);
            txtTrail = itemView.findViewById(R.id.txtTrailName);
            txtPlaces = itemView.findViewById(R.id.totalTrailPlaces);
            txtLength = itemView.findViewById(R.id.txtLength);
            txtDays = itemView.findViewById(R.id.AvgDays);
        }
    }

}
