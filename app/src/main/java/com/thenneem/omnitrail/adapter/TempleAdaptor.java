package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.FullscreenActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.TempleHome;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;
import com.thenneem.omnitrail.rest.ItemClickListner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TempleAdaptor  extends RecyclerView.Adapter<TempleAdaptor.TempleViewHolder>
implements Filterable
{

    private List<Temple> temples;
    private List<Temple> templesFiltered;

    private int rowLayout;
    private Context context;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    templesFiltered = temples;
                } else {
                    List<Temple> TemplefilteredList = new ArrayList<>();
                    for (Temple row : temples) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTempleName().toLowerCase().contains(charString.toLowerCase()) || row.getAddress().contains(charSequence)) {
                            TemplefilteredList.add(row);
                        }
                    }

                    templesFiltered = TemplefilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = templesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                templesFiltered = (ArrayList<Temple>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static  class TempleViewHolder extends RecyclerView.ViewHolder implements   View.OnClickListener {

        LinearLayout templeLayout;
        ImageView imgTempleView;
        TextView txtTempleName;
        TextView txtTempleStory;
        TextView txtLocation;
        TextView txtPrimaryDeity;
        TextView txtSect;
        LinearLayout llDeity;
        MaterialButton btnMore;



        private ItemClickListner itemClickListner;

        public TempleViewHolder(@NonNull View itemView) {
            super(itemView);


            templeLayout  = itemView.findViewById(R.id.templelist_layout);
            imgTempleView = itemView.findViewById(R.id.templeThumb);
            txtTempleName = itemView.findViewById(R.id.txtTempleName);
           // txtTempleStory = itemView.findViewById(R.id.txtTempleStory);
           // txtSect = (TextView)itemView.findViewById(R.id.txtSect);
            txtLocation = itemView.findViewById(R.id.txtMyLocation);
            txtPrimaryDeity = itemView.findViewById(R.id.txtPrimaryDeity);
            btnMore = itemView.findViewById(R.id.btnMore);
            //llDeity = itemView.findViewById(R.id.layoutDeity);
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
        this.templesFiltered = temples;
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
        //holder.txtTempleStory.setText(temples.get(position).getTempleName());
        //holder.txtTempleStory.setText(temples.get(position).getTempleStory());
        ///holder.txtSect.setText(temples.get(position).);

        holder.txtLocation.setText(temples.get(position).getCity_name() + ", " +  temples.get(position).getState_name() + "," + temples.get(position).getCountry_name()  );
        if(temples.get(position).getPrimaryDeity()!="")
        {
           // holder.llDeity.setVisibility(View.VISIBLE);
            holder.txtPrimaryDeity.setText(temples.get(position).getPrimaryDeity());}
        else {
           // holder.llDeity.setVisibility(View.GONE);
        }



        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();

        //getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        Picasso.Builder builder = new Picasso.Builder(this.context);
        builder.build().load(temples.get(position).getTempleIMG())
                .centerCrop()
                .resize(height,width)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgTempleView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //Log.d("Image temple","piccaso Success" );
                    }

                    @Override
                    public void onError(Exception e) {
                        //Log.d("Image Temple","piccaso error" + e.getMessage() );
                        Toast.makeText(context, "Piccaso Error "  + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

       /*
        final Temple tmpl = temples.get(position);


holder.btnMore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, TempleHome.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Temple",tmpl);


        context.startActivity(intent);
    }
});

        */


        //estup item click listner
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {

               // Toast.makeText(context, " Clicked " + temples.get(position).getTempleName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, TempleHome.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Temple",temples.get(position));


                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return temples.size();
    }
}
