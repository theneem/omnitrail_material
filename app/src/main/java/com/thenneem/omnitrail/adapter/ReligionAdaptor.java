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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.FullscreenActivity;
import com.thenneem.omnitrail.NewObjectActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.ReligionHome;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.rest.ItemClickListner;

import org.w3c.dom.Text;


public class ReligionAdaptor extends RecyclerView.Adapter<ReligionAdaptor.ReligionViewHolder> {

    private List<Religion> religions;
    private int rowLayout;
    private Context context;


    public static class ReligionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout religionLayout;
        ImageView imgView;
        TextView txtRName;
        TextView txtRDesc;
        TextView txtRSaint;
        TextView txtRTemple;
        ImageView moreButton;

        private ItemClickListner itemClickListner;

        public ReligionViewHolder(View v) {

            super(v);
            religionLayout = v.findViewById(R.id.religion_layout);
            imgView = v.findViewById(R.id.religionThumb);
            txtRName = v.findViewById(R.id.txtReligionName);
            txtRDesc = v.findViewById(R.id.txtReligionDesc);
            txtRSaint = v.findViewById(R.id.txtRSaint);
            txtRTemple = v.findViewById(R.id.txtRTemple);
            moreButton = v.findViewById(R.id.moreButton);

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


        Toast.makeText(context, "Displaying Religion name", Toast.LENGTH_LONG).show();
        Toast.makeText(context, religions.get(position).getReligionName(), Toast.LENGTH_LONG).show();
        holder.txtRName.setText(religions.get(position).getReligionName());
        holder.txtRDesc.setText(religions.get(position).getReligionDesc());
        holder.txtRSaint.setText("Saints (" + religions.get(position).getNoofsaint() + ")");
        holder.txtRTemple.setText("Temples (" + religions.get(position).getNooftemple() + ")");
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

        holder.moreButton.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            menu.getMenu().add(0, 0, 0, "Add Temple");
            menu.getMenu().add(0, 1, 1, "Add Saint");
            menu.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(v.getContext(), NewObjectActivity.class);
                intent.putExtra("religion", religions.get(position));
                switch (item.getItemId()){
                    case 0:{
                        intent.putExtra("type", 0);
                        break;
                    }
                    case 1:{
                        intent.putExtra("type", 1);
                        break;
                    }
                }
                v.getContext().startActivity(intent);
                return false;
            });
            menu.show();
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

    @Override
    public int getItemCount() {
        return religions.size();
    }
}
