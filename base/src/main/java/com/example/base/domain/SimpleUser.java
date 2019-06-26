package com.example.base.domain;

import com.example.base.domain.executor.Executor;
import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;

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
