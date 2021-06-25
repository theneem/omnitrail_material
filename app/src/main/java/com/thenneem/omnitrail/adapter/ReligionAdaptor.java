package com.thenneem.omnitrail.adapter;

import android.app.AlertDialog;

//import android.support.v7.app.AlertDialog;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.AddSaintActivity;
import com.thenneem.omnitrail.AddTempleActivity;
import com.thenneem.omnitrail.FullscreenActivity;
import com.thenneem.omnitrail.LoginActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.ReligionHome;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.rest.ItemClickListner;
import com.thenneem.omnitrail.utils.PreferenceManager;


public class ReligionAdaptor extends RecyclerView.Adapter<ReligionAdaptor.ReligionViewHolder> {

    private List<Religion> religions;
    private int rowLayout;
    private Context context;
    PreferenceManager preferenceManager;


    public static class ReligionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout religionLayout;
        ImageView imgView;
        TextView txtRName;
        TextView txtRDesc;
        TextView txtRSaint;
        TextView txtRTemple;
        TextView txtRTrail;
        ImageView moreButton;
        ImageView btnAddTemple;
        ImageView btnAddSaint;
        FloatingActionButton fabAddTemple;


        private ItemClickListner itemClickListner;

        public ReligionViewHolder(View v) {

            super(v);
            religionLayout = v.findViewById(R.id.religion_layout);
            imgView = v.findViewById(R.id.religionThumb);
            txtRName = v.findViewById(R.id.txtReligionName);
            txtRDesc = v.findViewById(R.id.txtReligionDesc);
            txtRSaint = v.findViewById(R.id.txtRSaint);
            txtRTemple = v.findViewById(R.id.txtRTemple);
            txtRTrail = v.findViewById(R.id.txtRTrail);
            btnAddSaint = v.findViewById(R.id.btn_addSaint);
            btnAddTemple = v.findViewById(R.id.btn_addTemple);
            fabAddTemple = v.findViewById(R.id.fabAddTemple);
            v.setOnClickListener(this);


        }


        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;

        }

        @Override
        public void onClick(View v) {

            itemClickListner.onClick(v, getAdapterPosition());
        }
    }

    public ReligionAdaptor(List<Religion> religions, int rowLayout, Context context) {
        this.religions = religions;
        this.rowLayout = rowLayout;
        this.context = context;


    }

    @NonNull
    @Override
    public ReligionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ReligionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReligionViewHolder holder, int position) {


        //Toast.makeText(context, "Displaying Religion name", Toast.LENGTH_LONG).show();
        //Toast.makeText(context, religions.get(position).getReligionName(), Toast.LENGTH_LONG).show();
        holder.txtRName.setText(religions.get(position).getReligionName());
        holder.txtRDesc.setText(religions.get(position).getReligionDesc());
        holder.txtRSaint.setText("Saints: " + religions.get(position).getNoofsaint() );
        holder.txtRTemple.setText("Temples: " + religions.get(position).getNooftemple() );
        holder.txtRTrail.setText("Trail:" + religions.get(position).getNooftrail());
        holder.txtRDesc.setVisibility(View.GONE);

        Picasso.Builder builder = new Picasso.Builder(this.context);
        //builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(religions.get(position).getHeaderimg())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //holder.mMediaEvidencePb.setVisibility(View.GONE);
                        //Log.d("test1","piccaso Success" );
                        //Toast.makeText(context, "Piccaso success ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        //Log.d("test1","piccaso error" + e.getMessage() );
                        Toast.makeText(context, "Piccaso Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });





        holder.fabAddTemple.setOnClickListener(v -> {
            // we need to check the if already login or not

            preferenceManager = new PreferenceManager(v.getContext());


            if (preferenceManager.getLoginSession()) {
                showTemple(religions.get(position),v.getContext());
            }
            else {
                showPage("temple", religions.get(position), v.getContext());
            }
        });




        holder.btnAddTemple.setOnClickListener(v -> {
            // we need to check the if already login or not

            preferenceManager = new PreferenceManager(v.getContext());


            if (preferenceManager.getLoginSession()) {
                showTemple(religions.get(position),v.getContext());
            }
            else {
                showPage("temple", religions.get(position), v.getContext());
            }
        });


        holder.btnAddSaint.setOnClickListener(v -> {

            preferenceManager = new PreferenceManager(v.getContext());

            if (preferenceManager.getLoginSession()) {
                showSaint(religions.get(position),v.getContext());
            }
            else {
                showPage("saint",  religions.get(position),v.getContext());
            }




        });



        //estup item click listner
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {
                // Toast.makeText(context, " Clicked " + religions.get(position).getReligionName(), Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(context, ReligionHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Religion_Name", religions.get(position).getReligionName());
                intent.putExtra("Religion", religions.get(position));

                context.startActivity(intent);


            }
        });


    }

    public void showPage(String pageName,  Religion religisionName, Context cntx)
    {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Intent intent;
                        intent = new Intent(cntx, LoginActivity.class);
                        //intent.putExtra("type", 1);
                        //intent.putExtra("religion", religions.get(position));
                        cntx.startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        if(pageName == "temple") {

                            showTemple(religisionName,cntx);
                        }
                        else if (pageName == "saint")
                        {
                            showSaint(religisionName,cntx);

                        }
                        break;
                }
            }
        };

        AlertDialog.Builder abuilder = new AlertDialog.Builder(cntx);
        abuilder.setMessage("Would you like to Login ? press no to continue as annonymus user.").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void showSaint(Religion religisionName, Context cntx)
    {
        Intent intent;
        intent = new Intent(cntx, AddSaintActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("religion", religisionName);
        cntx.startActivity(intent);
    }


    public void showTemple(Religion religisionName, Context cntx)
    {

        Intent intent;
        intent = new Intent(cntx, AddTempleActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("religion", religisionName);
        cntx.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return religions.size();
    }
}
