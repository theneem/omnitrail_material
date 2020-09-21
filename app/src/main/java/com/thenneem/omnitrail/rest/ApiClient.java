package com.thenneem.omnitrail.rest;


import android.app.Application;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiClient {

    public static final String BASE_URL =  "https://scienceclub.in/relisantapi/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            try {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder()
                        .addNetworkInterceptor(interceptor)
                        .build();
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }
            catch (Exception e) {
                // This will catch any exception, because they are all descended from Exception
                //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                //Toast.makeText(, "Error" +  t.toString(),Toast.LENGTH_LONG).show();
               throw  e;


                //return null;
            }
            
            }
        return retrofit;
    }


}
