package com.example.testmodule.livedata;

import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.common.ObserverLiveData;
import com.example.common.ArouterConstant;
import com.example.base.aac.BaseActivity;
import com.example.testmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;



@Route(path = ArouterConstant.TestActivity2)
public class TestActivity2 extends BaseActivity {
    private LoginViewModel loginViewModel;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView tvRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        tvRoom = findViewById(R.id.tvRoom);
        tvRoom.setOnClickListener(view -> {

            UserDao userDao = UserDataBases.getInstance().userDao();
            UserEntity byUid = userDao.findByUid(0);
            if(byUid!=null){
                tvRoom.setText(byUid.toString());
            }
        });
        loginViewModel = get(LoginViewModel.class);
        loginViewModel.login("18500137108","123456");
        loginViewModel.getLoginMutableLiveData().observe(this, new ObserverLiveData<UserEntity>() {
            @Override
            public void onChangedSuccess(UserEntity model) {
                Toast.makeText(TestActivity2.this, model.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangedFailure(UserEntity throwable) {
                Toast.makeText(TestActivity2.this, throwable.message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test2;
    }


}
