package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ItemClickListner;

import java.util.List;

public class TempleAdaptor  extends RecyclerView.Adapter<TempleAdaptor.TempleViewHolder> {

    private List<Temple> temples;
    private int rowLayout;
    private Context context;

    public static  class TempleViewHolder extends RecyclerView.ViewHolder implements   View.OnClickListener {

        LinearLayout templeLayout;
        ImageView imgTempleView;
        TextView txtTempleName;
        TextView txtTempleStory;


        private ItemClickListner itemClickListner;
        public TempleViewHolder(@NonNull View itemView) {
            super(itemView);


            templeLayout  = (LinearLayout) itemView.findViewById(R.id.templelist_layout);
            imgTempleView = (ImageView)itemView.findViewById(R.id.templeThumb);
            txtTempleName = (TextView) itemView.findViewById(R.id.txtTempleName);
            txtTempleStory =(TextView)itemView.findViewById(R.id.txtTempleStory);


            itemView.setOnClickListener(this);

        }
        public void     setItemClickListner(ItemClickListner itemClickListner)
        {
            this.itemClickListner = itemClickListner;

        }


        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v,getAdapterPosition());
        }
    }

    public TempleAdaptor(List<Temple> temples, int rowLayout, Context context)
    {
        this.temples = temples;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public TempleAdaptor.TempleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new TempleAdaptor.TempleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempleAdaptor.TempleViewHolder holder, int position) {


        holder.txtTempleName.setText(temples.get(position).getTempleName());
        holder.txtTempleStory.setText(temples.get(position).getTempleName());




        Picasso.Builder builder = new Picasso.Builder(this.context);
        //builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(temples.get(position).getTempleIMG())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgTempleView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Image temple","piccaso Success" );
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Image Temple","piccaso error" + e.getMessage() );
                        Toast.makeText(context, "Piccaso Error "  + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });





        //estup item click listner
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {

                //Toast.makeText(context, " Clicked " + saints.get(position).getSaintName(), Toast.LENGTH_SHORT).show();




            }
        });




    }

    @Override
    public int getItemCount() {
        return temples.size();
    }
}
