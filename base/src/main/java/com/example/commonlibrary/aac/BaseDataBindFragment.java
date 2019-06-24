package com.example.commonlibrary.aac;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public abstract  class BaseDataBindFragment<T extends ViewDataBinding> extends Fragment {
    private ViewModelProvider viewModelProvider;
    protected ViewDataBinding dataBind;
    protected  View  mView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelProvider = getViewModelProvider();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModelProvider = null;
        if(dataBind!=null){
            dataBind.unbind();
            dataBind = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBind = DataBindingUtil.inflate(inflater,getLayoutId(), container, false);
        return dataBind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView=view;
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

    protected  abstract  int getLayoutId();
}