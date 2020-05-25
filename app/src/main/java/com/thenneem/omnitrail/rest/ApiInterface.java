package com.thenneem.omnitrail.rest;


import com.thenneem.omnitrail.model.Book;
import com.thenneem.omnitrail.model.Event;
import com.thenneem.omnitrail.model.Feature;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("religionlist.php")
    Call<List<Religion>> getReligionList();


    @POST("saintlist.php")
    @FormUrlEncoded
    Call<List<Saint>> getSaintList(@Field("rid") String rid);


    @POST("booklist.php")
    @FormUrlEncoded
    Call<List<Book>> getBookList(@Field("rid") String rid);


    @POST("templelist.php")
    @FormUrlEncoded
    Call<List<Temple>> getTempleList(@Field("rid") String rid);



    @POST("gettemple.php")
    @FormUrlEncoded
    Call<List<Temple>> getTemple(@Field("tid") String tid);



    //get event list for the temples ? (may be we can use the same in future for saints
    @POST("eventlist.php")
    @FormUrlEncoded
    Call<List<Event>> getEventList(@Field("tid") String tid);

    //get event list for the temples ? (may be we can use the same in future for saints
    @POST("featurelist.php")
    @FormUrlEncoded
    Call<List<Feature>> getFeatureList(@Field("tid") String tid);






}
