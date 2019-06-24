package com.example.commonlibrary.aac;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class BaseFragment extends Fragment {
    private ViewModelProvider viewModelProvider;
    private Context  mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelProvider = getViewModelProvider();
    }
    @Override
    public void onDestroy() {
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
}