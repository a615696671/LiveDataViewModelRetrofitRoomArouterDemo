package com.example.testmodule.livedata;

import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.common.ImageUtils;
import com.example.common.ObserverLiveData;
import com.example.common.ArouterConstant;
import com.example.base.aac.BaseActivity;
import com.example.common.glide.ProgressListener;
import com.example.testmodule.R;
import com.example.testmodule.room.DaoActionResultListener;
import com.example.testmodule.room.DaoUtils;
import com.example.testmodule.room.UserEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;


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
        tvRoom.setOnClickListener((View view) -> DaoUtils.getAll(resultList -> {
            if(resultList!=null&&resultList.size()>0){
                tvRoom.setText(resultList.toString());
            }
        }));
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
        ImageView iv = findViewById(R.id.iv);
        ImageUtils.loadImage(mContext, iv,
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565927064&di=6e34626b576c75158d756c1ad86d5c6e&imgtype=jpg&er=1&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fc934d5bba91360044978d1645e0282c4595d2d57.jpg");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test2;
    }


}
