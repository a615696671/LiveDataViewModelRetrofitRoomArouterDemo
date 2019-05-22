package com.example.testmodule;

import com.example.commonlibrary.http.BaseBean;
import com.example.commonlibrary.http.RetrofitCallback;
import com.example.commonlibrary.http.RetrofitHelper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel  extends ViewModel {
    MutableLiveData<BaseBean> liveData;
    public LoginViewModel() {
        liveData = new MutableLiveData<>();
    }
    public LiveData<BaseBean> getLiveData(){
        return liveData;
    }
    public  void  login (LoginParams  params){
        RetrofitHelper.getService(ApiStore.class).login().enqueue(new RetrofitCallback<BaseBean<UserEntity>>() {
            @Override
            public void onSuccess(BaseBean<UserEntity> model) {
                model.getData().setId(0);
                UserDataBases.getInstance().userDao().update(model.getData());
                liveData.setValue(model);

            }
            @Override
            public void onThrowable(Throwable t, String message) {
                BaseBean<UserEntity> model  = new BaseBean<>();
                liveData.setValue(model);
            }
        });
    }
}
