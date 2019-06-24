package com.example.commonlibrary.aac;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseActivity extends AppCompatActivity {
    private ViewModelProvider viewModelProvider;
    protected Context mContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        mContext=this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        viewModelProvider = getViewModelProvider();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModelProvider = null;
    }
    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    public <T extends ViewModel> T get(Class<T> clazz){
        return viewModelProvider.get(clazz);
    }
    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private ViewModelProvider getViewModelProvider(){

        return ViewModelProviders.of(this);
    }
    /**
     * 获取布局ID
     * @return
     */
    protected abstract int getLayoutId();
}