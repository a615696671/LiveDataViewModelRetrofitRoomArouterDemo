package com.example.testmodule;

import com.example.commonlibrary.http.BaseBean;
import com.example.commonlibrary.http.RetrofitCallback;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiStore {
    @POST("user/login")
    Call<BaseBean<UserEntity>> login(@Body RequestBody body);
}
