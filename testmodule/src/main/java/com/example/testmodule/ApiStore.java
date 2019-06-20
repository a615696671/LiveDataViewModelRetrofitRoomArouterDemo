package com.example.testmodule;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiStore {
    @POST("user/login")
    Call<UserEntity> login(@Body RequestBody body);
}
