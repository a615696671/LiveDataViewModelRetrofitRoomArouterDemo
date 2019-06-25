package com.example.common.httpexpand;


import androidx.annotation.NonNull;

import com.example.common.BaseApplication;
import com.example.commonlibrary.utils.AppUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class RewriteCacheControlInterceptor implements Interceptor {


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static CacheControl getCacheControl() {
        return AppUtils.isNetConnected(BaseApplication.getContext()) ? CacheControl.FORCE_NETWORK:CacheControl.FORCE_CACHE;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return   chain.proceed(chain.request().newBuilder()
                .cacheControl(getCacheControl())
                .build()).newBuilder().build();

    }
}
