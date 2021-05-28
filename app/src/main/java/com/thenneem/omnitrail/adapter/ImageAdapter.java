package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.model.TempleImages;
import com.thenneem.omnitrail.utils.PicassoClient;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>{
    private final Context context;
    private List<TempleImages> templeImages = new ArrayList<>();

    public ImageAdapter(Context context, List<TempleImages> templeImages) {
        this.context = context;
        this.templeImages = templeImages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            TempleImages images = templeImages.get(position);

            PicassoClient.downloadImage(context,images.getImgURL(),holder.imgTemple);
    }

    @Override
    public int getItemCount() {
        return templeImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgTemple;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTemple =itemView.findViewById(R.id.imgTemple);
        }
    }
}
