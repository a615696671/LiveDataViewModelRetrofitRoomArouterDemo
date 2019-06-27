package com.example.testmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ArouterConstant;
import com.example.base.aac.BaseDataBindFragment;
import com.example.testmodule.cordova.CordovaMainActivity;
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
     mView.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(mContext,CordovaMainActivity.class));
         }
     });
    }

    @Override
    protected void initData() {


    }
}
