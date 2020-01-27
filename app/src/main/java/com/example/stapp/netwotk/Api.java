package com.example.stapp.netwotk;

import com.example.stapp.models.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    //baseurl

    String BASE_URL=" https://reqres.in/api/";

    @GET("users")
    Call <Model> getdata(@Query("page") int page);
}
