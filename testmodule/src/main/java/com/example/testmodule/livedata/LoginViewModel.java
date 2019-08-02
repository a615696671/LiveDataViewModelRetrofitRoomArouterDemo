package com.example.testmodule.livedata;


import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.common.httpexpand.RetrofitCallback;
import com.example.common.httpexpand.RetrofitHelper;
import com.example.base.aac.BaseViewModel;
import com.example.testmodule.ApiStore;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<UserEntity> getLoginMutableLiveData(){return get(UserEntity.class);}

    public  void  login (String name,String password){
        RetrofitHelper.getService(ApiStore.class).login(name,password).enqueue(new RetrofitCallback<UserEntity>() {
            @Override
            public void onSuccess(final UserEntity model) {
                ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> {
                    interactor.execute();
                }, MainThreadImpl.getInstance()) {
                    @Override
                    public void run() {
                        UserDao userDao = UserDataBases.getInstance().userDao();
                        //登陆数据子线程插入
                        if(userDao.findByUid(0)!=null){
                            userDao.update(model);
                        }else{
                            userDao.insert(model);
                        }
                        mMainThread.post(() -> getLoginMutableLiveData().setValue(model));
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
