package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlibrary.ArouterConstant;

@Route(path = ArouterConstant.MainActivity)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ARouter.getInstance().build(ArouterConstant.TestActivity).navigation();
             }
         });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ArouterConstant.TestActivity2).navigation();
            }
        });

    }
}
