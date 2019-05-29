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


@Route(path = ArouterConstant.TestActivity)
public class TestActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

    }
}
