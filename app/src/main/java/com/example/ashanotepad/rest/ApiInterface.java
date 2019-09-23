package com.example.ashanotepad.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface{

    @FormUrlEncoded
    @POST("api/v2/register")
    Call<RegistrationResponse>registerUser(@Field("email") String email,@Field("password") String password);


}
