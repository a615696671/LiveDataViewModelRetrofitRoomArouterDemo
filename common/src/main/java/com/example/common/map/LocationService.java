package com.example.common.map;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.common.ArouterConstant;
import com.example.common.alibabaprovider.ArouterCommonEditProvider;
import com.example.common.alibabaprovider.ArouterCommonProvider;
import com.example.base.utils.LogUtils;

/**
 * 创建日期：2017/12/17
 *
 * @author kong
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 a
 * 类说明：
 */
public class LocationService extends Service {
    private static final String TAG = "LocationService";
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private double latitude, longitude;
    private AMapLocationListener mLocationListener;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocationBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    public class LocationBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    private void getLocation() {
        //声明定位回调监听器
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        longitude = amapLocation.getLongitude();//获取经度
                        latitude = amapLocation.getLatitude();//获取纬度
                        LocationServiceBean locationServiceBean = new LocationServiceBean();
                        locationServiceBean.setAdCode(amapLocation.getAdCode());
                        locationServiceBean.setLatitude(latitude+"");
                        locationServiceBean.setLongitude(longitude+"");
                        locationServiceBean.setPro(amapLocation.getProvince());
                        locationServiceBean.setCurrentCity(amapLocation.getCity());
                        locationServiceBean.setAddress(amapLocation.getAddress());
                        locationServiceBean.setDistrict(amapLocation.getDistrict());
                        locationServiceBean.setAoiName(amapLocation.getAoiName());
                        locationServiceBean.setCurrentPosition(amapLocation.getDistrict() + amapLocation.getAoiName());
                        final ArouterCommonEditProvider arouterCommonEditProvider = (ArouterCommonEditProvider) ARouter.getInstance().build(ArouterConstant.ArouterCommonEditProviderImpl).navigation();
                        arouterCommonEditProvider.saveLocationData(locationServiceBean);
                        LogUtils.e(TAG, locationServiceBean.toString());
                        GetLocation  location=new GetLocation();
                        location.setL(new GetLocation.OnLocationSuccess() {
                            @Override
                            public void getLocationSuccess(Object[] objects) {

                            }
                            @Override
                            public void getLoacalCityCode(String cityCode) {
                                ArouterCommonProvider arouterCommonProvider  = (ArouterCommonProvider) ARouter.getInstance().build(ArouterConstant.ArouterCommonProviderImpl).navigation();
                                LocationServiceBean locationData = arouterCommonProvider.getLocationData();
                                if(null!=locationData){
                                    locationData.setCityCode(cityCode);
                                    LogUtils.e(TAG,locationData.toString());
                                    if(!arouterCommonEditProvider.saveLocationData(locationData)){
                                        //存储失败
                                        LogUtils.e(TAG,"LocationService GetLocation save data failure" );
                                    }
                                }
                            }
                            @Override
                            public void getLocationError() {

                            }
                        });
                        location.getLoacation(getApplicationContext(),amapLocation.getCity(),amapLocation.getCityCode());
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.i(TAG, "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(30000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端。
    }
}
