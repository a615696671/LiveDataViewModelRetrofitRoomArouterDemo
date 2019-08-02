package com.example.sinoservices.app;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.ArouterConstant;
import com.example.base.domain.executor.Executor;
import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.base.mvp.CheckPermissionsActivity;

@Route(path = ArouterConstant.SplashActivity)
public class SplashActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                    mMainThread.post(() -> {
                        ARouter.getInstance().build(ArouterConstant.MainActivity).navigation();
                        finish();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
