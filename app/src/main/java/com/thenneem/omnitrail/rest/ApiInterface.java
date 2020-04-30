package com.thenneem.omnitrail.rest;


import com.thenneem.omnitrail.model.Religion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("religionlist.php")
    Call<List<Religion>> getReligionList();


}
