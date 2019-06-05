package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlibrary.ArouterConstant;

@Route(path = ArouterConstant.MainActivity)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
