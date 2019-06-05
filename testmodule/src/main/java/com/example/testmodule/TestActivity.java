package com.example.testmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlibrary.ArouterConstant;
import com.example.commonlibrary.NetDataUtils;
import com.example.commonlibrary.http.BaseBean;
import com.example.ftpmodule.DownLoadUtils;


@Route(path = ArouterConstant.TestActivity)
public class TestActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DownLoadUtils downLoadUtils=new DownLoadUtils();
        downLoadUtils.installApk(this);
        downLoadUtils.setmDownLoadListener(new DownLoadUtils.DownLoadListener() {
            @Override
            public void onDownLoadListener(int progress) {
                //未验证进度条，功能是是否OK
            }
        });
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getLiveData().observe(this, new Observer<BaseBean>() {
            @Override
            public void onChanged(BaseBean baseBean) {

            }
        });
    }
}
