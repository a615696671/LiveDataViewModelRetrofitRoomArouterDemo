package com.example.commonlibrary.domain;

import com.example.commonlibrary.domain.executor.Executor;
import com.example.commonlibrary.domain.executor.impl.ThreadExecutor;
import com.example.commonlibrary.domain.interactors.base.AbstractInteractor;
import com.example.commonlibrary.domain.threading.MainThreadImpl;

public class SimpleUser {

    public void simpleUser(){
        ThreadExecutor.getInstance().execute(new AbstractInteractor(new Executor() {
            @Override
            public void execute(AbstractInteractor interactor) {
                interactor.execute();
                //子线程操作

            }
        }, MainThreadImpl.getInstance()) {
            @Override
            public void run() {
             //主线程操作

            }
        });
    }

}
