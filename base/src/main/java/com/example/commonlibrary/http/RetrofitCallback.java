package com.example.commonlibrary.http;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 创建日期：2018/1/30 0030 下午 4:48
 *
 * @author kong
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 RetrofitCallback
 * 类说明：RetrofitCallback implements Callback
 */
public abstract class RetrofitCallback<M extends BaseBean> implements Callback<M> {
    public abstract void onSuccess(M model);
    public abstract void onThrowable(Throwable t, String message);
    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        if(response!=null&&response.body()!=null){
            if(response.body().flag==0){
                onSuccess(response.body());
            }else {
                onThrowable(new Throwable(response.body().flag+""),response.body().msg);
            }
        }else{
            onThrowable(new Throwable("response on a null object reference"),"please  check url!");
        }
    }
    @Override
    public void onFailure(Call<M> call, Throwable t) {
       onThrowable(t,t.getMessage());
    }
}
