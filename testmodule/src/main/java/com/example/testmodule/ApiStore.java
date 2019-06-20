package com.example.testmodule;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiStore {
    @FormUrlEncoded
    @POST("user/checkLogin")
    Call<UserEntity> login(@Field("accountName") String name,@Field("password")String password);
}
