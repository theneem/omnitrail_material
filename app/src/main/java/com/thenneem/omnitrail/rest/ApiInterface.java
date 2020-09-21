package com.thenneem.omnitrail.rest;


import com.thenneem.omnitrail.model.Book;
import com.thenneem.omnitrail.model.Event;
import com.thenneem.omnitrail.model.Feature;
import com.thenneem.omnitrail.model.Religion;
import com.thenneem.omnitrail.model.Saint;
import com.thenneem.omnitrail.model.Temple;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("getsaint.php")
    @FormUrlEncoded
    Call<List<Saint>> getSaint(@Field("sid") String sid);

    //get event list for the temples ? (may be we can use the same in future for saints
    @POST("eventlist.php")
    Call<List<Event>> getEventList(@Query("ptype") String ptype, @Query("pid") String pid);

    //get event list for the temples ? (may be we can use the same in future for saints
    @POST("featurelist.php")
    Call<List<Feature>> getFeatureList(@Query("ptype") String ptype, @Query("pid") String pid);

    //book search
    @POST("booksearch.php")
    Call<List<Book>> booksSearch(@Query("rid") String rid, @Query("query") String query);

    @POST("templesearch.php")
    Call<List<Temple>> templateSearch(@Query("rid") String rid, @Query("query") String query);

    @POST("saintsearch.php")
    Call<List<Saint>> saintSearch(@Query("rid") String rid, @Query("query") String query);

    @POST("templegeosearch.php")
    Call<List<Temple>> templateGeoSearch(@Query("rid") String rid, @Query("lang") String lon, @Query("lat") String lat, @Query("within") String radius);

    @POST("saintgeosearch.php")
    Call<List<Saint>> saintGeoSearch(@Query("rid") String rid, @Query("lang") String lon, @Query("lat") String lat, @Query("within") String radius);

    @POST("uploadimage.php")
    Call<UploadResult> uploadImage(@Query("itype") String type, @Body RequestBody image);

    @POST("templeadd.php")
    Call<Void> templeAdd(@Query("rid") String rid, @Query("TempleName") String templeName,
                         @Query("TempleIMG") String templeImage, @Query("PrimaryDeity") String deity,
                         @Query("TempleStory") String story, @Query("lang") Double lang, @Query("lat") Double lat);

}
