package com.example.commonlibrary.utils;

import com.example.commonlibrary.BaseRequestParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.RequestBody;
public class NetDataUtils {
    public static Gson gson = new GsonBuilder().create();
    public static <T extends BaseRequestParams> RequestBody createJson(T requestParams) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gson.toJson(requestParams));
        return body;
    }
}
