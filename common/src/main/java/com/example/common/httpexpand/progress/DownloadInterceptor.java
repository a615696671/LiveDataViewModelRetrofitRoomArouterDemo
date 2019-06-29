package com.example.common.httpexpand.progress;

import androidx.annotation.NonNull;

import com.example.base.utils.AppUtils;
import com.example.common.BaseApplication;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

//下载拦截器
public class DownloadInterceptor implements Interceptor {


    private OnDownloadListener mListener;

    public DownloadInterceptor( OnDownloadListener listener) {
        mListener = listener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //封装ressponse对象
        Response response = wrapResponse(chain.proceed(chain.request()));
        return response;
    }

    private Response wrapResponse(Response response) {
        if (response == null || response.body() == null) {
            return response;
        }
        //获取处理后的response对象
        Response wrapResponse = getWrapResponse(response);
        return wrapResponse;
    }

    private Response getWrapResponse(Response response) {
        ProgressInfo info = new ProgressInfo();
        info.setTime(System.currentTimeMillis()+"");
        info.setUrl(response.request().url().toString());
        Response.Builder builder = response.newBuilder();
        //封装responseBody，传入相关参数，获取进度数据回调
        return builder.body(new WrapResponseBody(response.body(),info,mListener)).build();
    }
}

