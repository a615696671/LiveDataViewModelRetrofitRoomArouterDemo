package com.example.common;


import com.example.commonlibrary.ContentValue;
import com.example.commonlibrary.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 创建日期：2018/1/30 0030 下午 4:48
 *
 * @author Eddy.Kang
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 RetrofitCallback
 * 类说明：RetrofitCallback implements Callback
 */
public abstract class RetrofitCallback<M extends BaseBean> implements Callback<M> {
    public abstract void onSuccess(M model);
    public abstract void onThrowable(M  throwable);
    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        if(response!=null&&response.body()!=null){
            if(response.body().flag== ContentValue.FLAG_SUCCESS){
                onSuccess(response.body());
            }else {
                M body = response.body();
                onThrowable(body);
            }
        }else{
            try {
                Class<M> mClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                Throwable throwable = new Throwable("response on a null object reference");
                M m = mClass.newInstance();
                m.t=throwable;
                m.msg="please  check url!";
                m.flag= ContentValue.FLAG_FAILURE;
                onThrowable(m);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onFailure(Call<M> call, Throwable t) {
        try {
            Class<M> mClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            LogUtils.d("mClass",mClass.getName());
            M m = mClass.newInstance();
            LogUtils.d("mClass",m.getClass().getName());
            m.flag= ContentValue.FLAG_FAILURE;
            m.t=t;
            m.msg=t.getMessage();
            onThrowable(m);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
