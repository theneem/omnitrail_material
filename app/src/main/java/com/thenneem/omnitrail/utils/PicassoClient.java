package com.thenneem.omnitrail.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thenneem.omnitrail.R;

public class PicassoClient {
    public static void downloadImage(Context c, String url, ImageView img){
        if(url != null && url.length()>0)
        {
            Picasso.get()
                    .load(url)
                    .fit()
                    .placeholder(R.drawable.placeholder).into(img);

        }else {
            Picasso.get().load(R.drawable.placeholder).into(img);
        }
    }
}
