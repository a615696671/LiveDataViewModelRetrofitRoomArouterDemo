package com.example.testmodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;


import com.amap.api.maps.MapView;
import com.example.commonlibrary.ArouterConstant;
import com.example.commonlibrary.BaseRequestParams;
import com.example.commonlibrary.aac.BaseActivity;
import com.example.commonlibrary.utils.NetDataUtils;

@Route(path = ArouterConstant.TestActivity)
public class TestActivity extends BaseActivity {
    private MapView mMapView;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
         mMapView.onCreate(savedInstanceState);
//        DownLoadUtils downLoadUtils=new DownLoadUtils();
//        downLoadUtils.installApk(this);
//        downLoadUtils.setmDownLoadListener(new DownLoadUtils.DownLoadListener() {
//            @Override
//            public void onDownLoadListener(int progress) {
//                //未验证进度条，功能是是否OK
//            }
//        });


        loginViewModel = get(LoginViewModel.class);
        loginViewModel.login(NetDataUtils.createJson(new BaseRequestParams() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }));

        loginViewModel.getPersonMutableLiveData().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity user) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
