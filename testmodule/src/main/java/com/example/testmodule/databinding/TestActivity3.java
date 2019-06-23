package com.example.testmodule.databinding;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ArouterConstant;
import com.example.commonlibrary.aac.BaseDataBindActivity;
import com.example.testmodule.R;
import com.example.testmodule.livedata.UserEntity;


@Route(path = ArouterConstant.TestActivity3)
public class TestActivity3 extends BaseDataBindActivity<ActivityTest3Binding> {

    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userEntity = new UserEntity();
        userEntity.setNickname("userEntity");
        dataBind.setUserEntity(userEntity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test3;
    }

    public void onClick(View view){
        Toast.makeText(this, "111111", Toast.LENGTH_SHORT).show();
        userEntity.setNickname("userEntityBinding");
    }
}
