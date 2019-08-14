package com.example.testmodule.livedata;
import com.example.common.httpexpand.RetrofitCallback;
import com.example.common.httpexpand.RetrofitHelper;
import com.example.base.aac.BaseViewModel;
import com.example.testmodule.ApiStore;
import com.example.testmodule.room.DaoUtils;


import com.example.testmodule.room.UserEntity;

import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<UserEntity> getLoginMutableLiveData(){return get(UserEntity.class);}

    public  void  login (String name,String password){
        RetrofitHelper.getService(ApiStore.class).login(name,password).enqueue(new RetrofitCallback<UserEntity>() {
            @Override
            public void onSuccess(final UserEntity model) {

                    DaoUtils.insertEntity(model, () ->getLoginMutableLiveData().setValue(model));
            }
            @Override
            public void onThrowable(UserEntity throwable) {
                getLoginMutableLiveData().setValue(throwable);
            }
        });
    }
}
