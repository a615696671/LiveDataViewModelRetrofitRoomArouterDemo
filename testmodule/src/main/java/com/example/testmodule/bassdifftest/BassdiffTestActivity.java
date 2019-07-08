package com.example.testmodule.bassdifftest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.base.ActivityLifeCallBack;
import com.example.base.domain.executor.Executor;
import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.base.utils.AppUtils;
import com.example.common.ArouterConstant;
import com.example.common.BaseApplication;
import com.example.common.bassdiff.BassdiffUtils;
import com.example.common.map.LocationService;
import com.example.testmodule.R;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

@Route(path = ArouterConstant.BassdiffTestActivity)
public class BassdiffTestActivity extends AppCompatActivity {

    //旧版本
    String old =getSaveLocation() + "/oldApk.apk";
    //新版本
    String newp = getSaveLocation()+ "/newApk.apk";
    //差分包
    String patch =getSaveLocation() + "/patchApk.patch";
    //旧版apk和差分包合并生成的新版apk
    String tmp = getSaveLocation()  + "/plusNewApk.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bassdiff_test);
        findViewById(R.id.btn2).setOnClickListener(view -> {

             //开始合并
            ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> {
                interactor.execute();
            }, MainThreadImpl.getInstance()) {
                @Override
                public void run() {
                    BassdiffUtils.patch(old,newp,patch);
                    mMainThread.post(() -> Toast.makeText(BassdiffTestActivity.this, "合并成功!", Toast.LENGTH_SHORT).show());
                }
            });
        });

        findViewById(R.id.btn1).setOnClickListener(view -> {

                    ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> {
                        interactor.execute();

                    }, MainThreadImpl.getInstance()) {
                @Override
                public void run() {
                    //开始差分
                    BassdiffUtils.diff(old,tmp,patch);
                   mMainThread.post(() -> Toast.makeText(BassdiffTestActivity.this, "差分成功!", Toast.LENGTH_SHORT).show());
                }
            });
        });

    }
    private String getSaveLocation() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
