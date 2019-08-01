package com.example.testmodule.databinding;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.ArouterConstant;
import com.example.common.alibabaprovider.ArouterCommonProvider;
import com.example.common.map.LocationServiceBean;
import com.example.base.aac.BaseDataBindActivity;
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
        findViewById(R.id.btn1).setOnClickListener(view -> {
            ArouterCommonProvider arouterCommonProvider= (ArouterCommonProvider) ARouter.getInstance().build(ArouterConstant.ArouterCommonProviderImpl).navigation();
            LocationServiceBean locationData = arouterCommonProvider.getLocationData();
            ((Button)view).setText(locationData.toString());
        });
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
