package com.example.testmodule;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.MapView;

import com.example.commonlibrary.ArouterConstant;

@Route(path = ArouterConstant.TestActivity)
public class TestActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        DownLoadUtils downLoadUtils=new DownLoadUtils();
//        downLoadUtils.installApk(this);
//        downLoadUtils.setmDownLoadListener(new DownLoadUtils.DownLoadListener() {
//            @Override
//            public void onDownLoadListener(int progress) {
//                //未验证进度条，功能是是否OK
//            }
//        });
//        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
//        loginViewModel.getLiveData().observe(this, new Observer<BaseBean>() {
//            @Override
//            public void onChanged(BaseBean baseBean) {
//
//            }
//        });

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
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
