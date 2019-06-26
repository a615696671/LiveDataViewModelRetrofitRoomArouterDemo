package com.example.testmodule.livedata;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.ActivityLifeCallBack;
import com.example.base.domain.executor.Executor;
import com.example.base.domain.executor.MainThread;
import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.base.utils.AppUtils;
import com.example.base.widget.SmartRefreshLayoutDefaultSetting;
import com.example.common.BaseApplication;
import com.example.common.RetrofitCallback;
import com.example.common.httpexpand.RetrofitHelper;
import com.example.base.aac.BaseViewModel;
import com.example.common.map.LocationService;
import com.example.testmodule.ApiStore;

import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<UserEntity> getLoginMutableLiveData(){return get(UserEntity.class);}

    public  void  login (String name,String password){
        RetrofitHelper.getService(ApiStore.class).login(name,password).enqueue(new RetrofitCallback<UserEntity>() {
            @Override
            public void onSuccess(final UserEntity model) {
                ThreadExecutor.getInstance().execute(new AbstractInteractor(new Executor() {
                    @Override
                    public void execute(AbstractInteractor interactor) {
                        interactor.execute();
                        //登陆数据子线程插入
                        model.setId(0);
                        UserDataBases.getInstance().userDao().update(model);
                    }
                }, MainThreadImpl.getInstance()) {
                    @Override
                    public void run() {
                        getLoginMutableLiveData().setValue(model);
                    }
                });
            }
            @Override
            public void onThrowable(UserEntity throwable) {
                getLoginMutableLiveData().setValue(throwable);
            }
        });
    }
}
