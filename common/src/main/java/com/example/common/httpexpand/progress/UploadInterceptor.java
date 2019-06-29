package com.example.common.httpexpand.progress;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

//上传拦截器
public class UploadInterceptor implements Interceptor {
    private OnUploadListener mListener;

    public UploadInterceptor(OnUploadListener listener) {
        mListener = listener;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //封装request对象
        Request request = wrapRequest(chain.request());
        Response response = chain.proceed(request);
        return response;
    }


    private Request wrapRequest(Request request) {
        if (request == null || request.body() == null) {
            return request;
        }
        Request.Builder builder = request.newBuilder();
        ProgressInfo info = new ProgressInfo();
        HttpUrl url = request.url();
        info.setUrl(url.toString());
        info.setTime(System.currentTimeMillis()+"");
        //封装requestBody，传入参数，获取数据进度回调
        builder.method(request.method(),new WrapRequestBody(request.body(),info,mListener));
        return builder.build();
    }
}