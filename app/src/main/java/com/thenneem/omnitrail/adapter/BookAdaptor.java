package com.thenneem.omnitrail.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.thenneem.omnitrail.BookActivity;
import com.thenneem.omnitrail.R;
import com.thenneem.omnitrail.TempleHome;
import com.thenneem.omnitrail.model.Book;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.rest.ItemClickListner;

import java.util.List;

public class BookAdaptor extends RecyclerView.Adapter<BookAdaptor.BookViewHolder>    {


    private List<Book> books;
    private int rowLayout;
    private Context context;


public static class      BookViewHolder  extends RecyclerView.ViewHolder implements   View.OnClickListener  {


    LinearLayout bookLayout;
    ImageView imgBookThumb;
    TextView txtBookName;
    TextView txtAuthortName;
    TextView txtBookDesc;

    private ItemClickListner itemClickListner;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);

        bookLayout  = (LinearLayout) itemView.findViewById(R.id.book_layout);
        imgBookThumb = (ImageView)itemView.findViewById(R.id.bookThumb);
        txtBookName = (TextView) itemView.findViewById(R.id.txtBookName);
        txtAuthortName =(TextView)itemView.findViewById(R.id.txtAuthortName);
        txtBookDesc =(TextView)itemView.findViewById(R.id.txtBookDesc);

        itemView.setOnClickListener(this);

    }

    public void     setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;

    }




    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition());
        itemClickListner.onClick(v,getAdapterPosition());



    }
}


    public BookAdaptor(List<Book> books, int rowLayout, Context context)
    {
        this.books = books;
        this.rowLayout = rowLayout;
        this.context = context;
    }


    @NonNull
    @Override
    public BookAdaptor.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new BookAdaptor.BookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookAdaptor.BookViewHolder holder, int position) {


        holder.txtBookName.setText(books.get(position).getBookName());
        holder.txtAuthortName.setText(books.get(position).getAuthor());
        holder.txtBookDesc.setText(books.get(position).getBookDesc() );

        Picasso.Builder builder = new Picasso.Builder(this.context);
        //builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(books.get(position).getBookImg())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgBookThumb, new Callback() {
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


        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {

                // Toast.makeText(context, " Clicked " + temples.get(position).getTempleName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, BookActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Temple",books.get(position));


                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
