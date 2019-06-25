package com.example.testmodule.livedata;

import com.example.common.RetrofitCallback;
import com.example.commonlibrary.aac.BaseViewModel;
import com.example.testmodule.ApiStore;

import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel {


    public MutableLiveData<UserEntity> getLoginMutableLiveData(){return get(UserEntity.class);}

    public  void  login (String name,String password){
        RetrofitHelper.getService(ApiStore.class).login(name,password).enqueue(new RetrofitCallback<UserEntity>() {
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
