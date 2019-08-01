package com.example.common.httpexpand;


import com.example.base.utils.LogUtils;
import com.example.common.BaseBean;
import com.example.common.ContentValue;

import java.lang.reflect.ParameterizedType;

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
    private  String TAG="RetrofitCallback";
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
                    M m = mClass.newInstance();
                    m.flag= ContentValue.FLAG_FAILURE;
                    if(response==null){
                        m.msg=ContentValue.HttpMsgContentValue.HTTP_NO_ERROR;
                    }else{
                        m.httpStateCode=response.code();
                    }
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
            LogUtils.d(TAG,mClass.getName());
            M m = mClass.newInstance();
            LogUtils.d(TAG,m.getClass().getName());
            m.flag= ContentValue.FLAG_FAILURE;
            LogUtils.e(TAG,t.getStackTrace().toString());
            m.msg=t.getMessage();
            onThrowable(m);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
