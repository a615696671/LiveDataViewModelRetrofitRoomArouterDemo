package com.example.testmodule.bassdifftest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ArouterConstant;
import com.example.common.bassdiff.BassdiffUtils;
import com.example.testmodule.R;
@Route(path = ArouterConstant.BassdiffTestActivity)
public class BassdiffTestActivity extends AppCompatActivity {

    //旧版本
    String old =getSaveLocation() + "/oldApk.apk";
    //新版本
    String newp = getSaveLocation()+ "/newApk.apk";
    //差分包
    String patch =getSaveLocation() + "patchApk.patch";
    //旧版apk和差分包合并生成的新版apk
    String tmp = getSaveLocation()  + "plusNewApk.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bassdiff_test);
        findViewById(R.id.btn2).setOnClickListener(view -> {
             //开始合并
            BassdiffUtils.patch(old,newp,patch);
        });


        findViewById(R.id.btn1).setOnClickListener(view -> {
             //开始差分
            BassdiffUtils.diff(old,tmp,patch);


        });

    }
    private String getSaveLocation() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }



}
