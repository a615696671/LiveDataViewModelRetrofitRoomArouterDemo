package com.example.testmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.commonlibrary.ArouterConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

@Route(path = ArouterConstant.TestActivity2)
public class TestActivity2 extends AppCompatActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);

    }
}
