package com.thenneem.omnitrail.retrofit;

import com.thenneem.omnitrail.response.ForgotPassResponse;

import com.thenneem.omnitrail.response.RegisterResponse;
import com.thenneem.omnitrail.response.SocialLoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {



    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> getRegister(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<RegisterResponse> getLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("forgotPasswordOtp.php")
    Call<ForgotPassResponse> getForgotPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("resetPassword.php")
    Call<RegisterResponse> getResetPassword(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("socialLogin.php")
    Call<SocialLoginResponse> getSocialLogin(
            @Field("isSocial") String isSocial,
            @Field("username") String username,
            @Field("email") String email,
            @Field("userId") String userId
    );

}
