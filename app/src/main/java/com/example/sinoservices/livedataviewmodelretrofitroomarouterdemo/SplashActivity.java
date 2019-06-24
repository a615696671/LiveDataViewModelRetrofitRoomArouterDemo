package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo;


import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.ArouterConstant;
import com.example.commonlibrary.aac.BaseActivity;
import com.example.commonlibrary.domain.executor.Executor;
import com.example.commonlibrary.domain.executor.impl.ThreadExecutor;
import com.example.commonlibrary.domain.interactors.base.AbstractInteractor;
import com.example.commonlibrary.domain.threading.MainThreadImpl;
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThreadExecutor.getInstance().execute(new AbstractInteractor(new Executor() {
            @Override
            public void execute(AbstractInteractor interactor) {
                interactor.execute();
            }
        }, MainThreadImpl.getInstance()) {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mMainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            ARouter.getInstance().build(ArouterConstant.MainActivity).navigation();
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
