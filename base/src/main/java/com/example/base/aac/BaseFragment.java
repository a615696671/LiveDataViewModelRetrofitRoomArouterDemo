package com.example.base.aac;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.base.utils.LogUtils;

public abstract  class BaseFragment extends Fragment {
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.d("TAG",  " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    protected  abstract  void  initData();
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
    private Toast mToast;

    public void showToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

}