package com.example.testmodule.room;

import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;


import java.util.List;

public class DaoUtils {

    public static void  insertEntity(UserEntity t, DaoActionListener listener){
        ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> interactor.execute(), MainThreadImpl.getInstance()) {
            @Override
            public void run() {
                //子线程插入数据
                UserDao userDao = UserDataBases.getInstance().userDao();
                userDao.insert(t);
                mMainThread.post(() -> {
                 if(listener!=null){
                     listener.daoActionListenerSuccess();
                  }
                });
            }
        });
    }


    public static void  upEntity(UserEntity t, DaoActionListener listener){
        ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> interactor.execute(), MainThreadImpl.getInstance()) {
            @Override
            public void run() {
                //子线程插入数据
                UserDao userDao = UserDataBases.getInstance().userDao();
                userDao.update(t);
                mMainThread.post(() -> {
                    if(listener!=null){
                        listener.daoActionListenerSuccess();
                    }
                });
            }
        });
    }

    public static void  getAll(DaoActionResultListener listener){
        ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> interactor.execute(), MainThreadImpl.getInstance()) {
            @Override
            public void run() {
                //子线程插入数据
                UserDao userDao = UserDataBases.getInstance().userDao();
                List<UserEntity> all = userDao.getAll();
                mMainThread.post(() -> {
                    if(listener!=null){
                        listener.daoActionListenerSuccess(all);
                    }
                });
            }
        });
    }
}
