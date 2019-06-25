package com.example.testmodule;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ArouterConstant;
import com.example.commonlibrary.aac.BaseDataBindFragment;
import com.example.testmodule.databinding.TestTestFragmentLayoutBinding;
@Route(path = ArouterConstant.TestFragment)
public class TestFragment extends BaseDataBindFragment<TestTestFragmentLayoutBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.test_test_fragment_layout;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initData() {


    }
}
