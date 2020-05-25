package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.thenneem.omnitrail.TempleHome;
import com.thenneem.omnitrail.model.Event;
import com.thenneem.omnitrail.rest.ItemClickListner;

import java.security.cert.CertificateNotYetValidException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdaptor extends RecyclerView.Adapter<EventAdaptor.EventViewHolder> {

    private List<Event> events;
    private int rowLayout;
    private Context context;


    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout eventLayout;
        ImageView imgeventThumb;
        TextView txtEventName;
        TextView txtEventDetails;
        TextView txtEventTime;
        TextView txtEventDay;
        TextView txtEventMonth;

        private ItemClickListner itemClickListner;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);


            eventLayout = (LinearLayout) itemView.findViewById(R.id.eventLayout);
            imgeventThumb = (ImageView) itemView.findViewById(R.id.imgeventThumb);
            txtEventName = (TextView) itemView.findViewById(R.id.txtEventName);
            txtEventDetails = (TextView) itemView.findViewById(R.id.txtEventDetails);
            txtEventTime = (TextView) itemView.findViewById(R.id.txtEventTime);
            txtEventDay = (TextView) itemView.findViewById(R.id.txtEventDay);
            txtEventMonth = (TextView) itemView.findViewById(R.id.txtEventMonth);

            itemView.setOnClickListener(this);

        }

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;

        }


        @Override
        public void onClick(View v) {

        }
    }

    public EventAdaptor(List<Event> events, int rowLayout, int emptyLayout, Context context) {

        this.events = events;
        if (this.events.size() == 0)
            this.rowLayout = emptyLayout;
        else
            this.rowLayout = rowLayout;


        this.context = context;


    }

    @NonNull
    @Override
    public EventAdaptor.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new EventAdaptor.EventViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull EventAdaptor.EventViewHolder holder, int position) {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate = null;
        Date endDate = null;
        String strStartDate = null;
        String strEndDate = null;
        String strDay = null;
        String strMonth = null;

        try {
            startDate = format.parse(events.get(position).getEentStartDate());
            endDate = format.parse(events.get(position).getEventEndDate());

            format = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

            strStartDate = format.format(startDate);
            strEndDate = format.format(endDate);

            format = new SimpleDateFormat("dd");
            strDay = format.format(startDate);

            format = new SimpleDateFormat("MMM");
            strMonth = format.format(startDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.txtEventName.setText(events.get(position).getEventName());
        holder.txtEventDetails.setText(events.get(position).getEventDetails());
        holder.txtEventTime.setText(events.get(position).getEentStartDate() + " to " + events.get(position).getEventEndDate());
        holder.txtEventTime.setText(strStartDate + " to " + strEndDate);


        holder.txtEventDay.setText(strDay);
        holder.txtEventMonth.setText(strMonth);

        Picasso.Builder builder = new Picasso.Builder(this.context);
        builder.build().load(events.get(position).getEventImg())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgeventThumb, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        //Log.d("Image Temple","piccaso error" + e.getMessage() );
                        Toast.makeText(context, "Piccaso Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        //estup item click listner
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(context, " Clicked " + events.get(position).getTempleName(), Toast.LENGTH_SHORT).show();

             /*
                Intent intent = new Intent(context, TempleHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Temple",temples.get(position));
                context.startActivity(intent);
            */


            }
        });


    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
