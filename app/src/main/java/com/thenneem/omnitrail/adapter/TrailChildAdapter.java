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
import com.thenneem.omnitrail.model.TrailChild;
import com.thenneem.omnitrail.utils.PicassoClient;

import java.util.ArrayList;
import java.util.List;

public class TrailChildAdapter extends RecyclerView.Adapter<TrailChildAdapter.MyViewHolder>{
    private final Context context;
    private List<TrailChild> trailChildren = new ArrayList<>();

    public TrailChildAdapter(Context context,List<TrailChild> trailChildren) {
        this.context = context;
        this.trailChildren = trailChildren ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.map_raw, parent, false);
        return new TrailChildAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgTrailChild;
        private TextView txtTempleName, txtPrimaryDeity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTrailChild = itemView.findViewById(R.id.imgTempleMap);
            txtTempleName = itemView.findViewById(R.id.txtTempleNameMap);
            txtPrimaryDeity = itemView.findViewById(R.id.txtPrimaryDeityMap);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TrailChildAdapter.MyViewHolder holder, int position) {
        TrailChild trailChild = trailChildren.get(position);

        holder.txtTempleName.setText(trailChild.getTempleName());
        holder.txtPrimaryDeity.setText(trailChild.getPrimaryDeity());
        PicassoClient.downloadImage(context,trailChild.getTempleIMG(),holder.imgTrailChild);
    }

    @Override
    public int getItemCount() {
        return trailChildren.size();
    }
}
