package com.thenneem.omnitrail.adapter;


import android.content.ClipData;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.FullscreenActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.ReligionHome;
import com.thenneem.omnitrail.SaintHome;
import com.thenneem.omnitrail.TempleHome;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ItemClickListner;

import org.w3c.dom.Text;


public class SaintAdaptor  extends RecyclerView.Adapter<SaintAdaptor.SaintViewHolder>    {



    private List<Saint> saints;
    private int rowLayout;
    private Context context;


    public static  class SaintViewHolder extends RecyclerView.ViewHolder implements   View.OnClickListener {


        LinearLayout saintLayout;
        ImageView imgSaintView;
        TextView txtSName;
        TextView txtSaintDesc;
        TextView txtSectName;
        TextView txtSamudai;

        private ItemClickListner itemClickListner;

        public SaintViewHolder(@NonNull View itemView) {
            super(itemView);


            saintLayout  = (LinearLayout) itemView.findViewById(R.id.saint_layout);
            imgSaintView = (ImageView)itemView.findViewById(R.id.saintThumb);
            txtSName = (TextView) itemView.findViewById(R.id.txtSaintName);
            txtSaintDesc =(TextView)itemView.findViewById(R.id.txtSaintDesc);
            txtSectName =(TextView)itemView.findViewById(R.id.txtSectName);
            txtSamudai =(TextView)itemView.findViewById(R.id.txtSamudai);

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

    public SaintAdaptor(List<Saint> saints, int rowLayout, Context context)
    {
        this.saints = saints;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    @NonNull
    @Override
    public SaintAdaptor.SaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new SaintAdaptor.SaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaintAdaptor.SaintViewHolder holder, int position) {


        holder.txtSName.setText(saints.get(position).getSaintName());
        holder.txtSaintDesc.setText(saints.get(position).getSaintDesc());
        holder.txtSectName.setText("Sect: " + saints.get(position).getSectName() );
        holder.txtSamudai.setText("Samudai: " + saints.get(position).getSamudai() );
        holder.txtSaintDesc.setText(saints.get(position).getSaintDesc());



        Picasso.Builder builder = new Picasso.Builder(this.context);
        //builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(saints.get(position).getSaintIMG())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgSaintView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //Log.d("test1","piccaso Success" );
                    }

                    @Override
                    public void onError(Exception e) {
                        //Log.d("test1","piccaso error" + e.getMessage() );
                        Toast.makeText(context, "Piccaso Error "  + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });




        //estup item click listner
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {


                Toast.makeText(context, " Clicked " + saints.get(position).getSaintName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, SaintHome.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Saint",saints.get(position));

                context.startActivity(intent);


            }
        });





    }

    @Override
    public int getItemCount() {
        return saints.size();
    }
}
