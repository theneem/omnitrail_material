package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.model.Event;
import com.thenneem.omnitrail.model.Feature;
import com.thenneem.omnitrail.rest.ItemClickListner;

import java.util.List;

public class FeatureAdaptor   extends  RecyclerView.Adapter<FeatureAdaptor.FeatureViewHolder>{

    private List<Feature> features;
    private int rowLayout;
    private Context context;

    public static  class FeatureViewHolder extends RecyclerView.ViewHolder implements   View.OnClickListener {

        LinearLayout featureLayout;
        //ImageView imgeventThumb;
        TextView txtFeatureName;
        MaterialTextView txtFeatureDescription;

        private ItemClickListner itemClickListner;

        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);

            featureLayout  = itemView.findViewById(R.id.featureLayout);
            //featureLayout = (ImageView)itemView.findViewById(R.id.imgeventThumb);
            txtFeatureName = itemView.findViewById(R.id.txtFeatureName);
            txtFeatureDescription = itemView.findViewById(R.id.txtFeatureDescription);


            itemView.setOnClickListener(this);

        }

        public void setItemClickListner(ItemClickListner itemClickListner){
            this.itemClickListner = itemClickListner;
        }
        @Override
        public void onClick(View v) {

        }
    }

    public FeatureAdaptor(List<Feature> features, int rowLayout, Context context){

        this.features = features;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public FeatureAdaptor.FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new FeatureAdaptor.FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureAdaptor.FeatureViewHolder holder, int position) {

        holder.txtFeatureName.setText(features.get(position).getFeatureName());
        holder.txtFeatureDescription.setText(features.get(position).getFeatureDetails());

    }

    @Override
    public int getItemCount() {
        return features.size();
    }
}
