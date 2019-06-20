package com.example.testmodule;

import com.example.commonlibrary.aac.BaseViewModel;
import com.example.commonlibrary.http.BaseBean;
import com.example.commonlibrary.http.RetrofitCallback;
import com.example.commonlibrary.http.RetrofitHelper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.RequestBody;

public class LoginViewModel extends BaseViewModel {


    public MutableLiveData<UserEntity> getLoginMutableLiveData(){return get(UserEntity.class);}

    public  void  login (RequestBody params){
        RetrofitHelper.getService(ApiStore.class).login(params).enqueue(new RetrofitCallback<UserEntity>() {
            @Override
            public void onSuccess(UserEntity model) {
                model.setId(0);
                UserDataBases.getInstance().userDao().update(model);
                getLoginMutableLiveData().setValue(model);
            }

            @Override
            public void onThrowable(UserEntity throwable) {
                getLoginMutableLiveData().setValue(throwable);
            }

        });
    }
}
