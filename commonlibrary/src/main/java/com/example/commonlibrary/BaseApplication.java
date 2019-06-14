package com.example.commonlibrary;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonlibrary.map.LocationService;
import com.example.commonlibrary.utils.AppUtils;
import com.tencent.bugly.Bugly;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application{
    private static  boolean isDebug=true;
    private static Context  context;
    private LocationService locationService;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context=this;
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(ActivityLifeCallBack.getActivityLifeCallBack());
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        String bugly =  AppUtils.getMetaDataApplication(this,"bugly");
        Bugly.init(getApplicationContext(),bugly ,isDebug);
        bindService(new Intent(getContext(), BaseApplication.class), serviceConnection, Context.BIND_AUTO_CREATE);
        startService(new Intent(getContext(), LocationService.class));//开启定位服务
    }
    // 在Activity中，我们通过ServiceConnection接口来取得建立连接与连接意外丢失的回调
    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取服务的操作对象
            locationService = ((LocationService.LocationBinder) service).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 连接断开

        }
    };
    public boolean  isDebug(){
        return  isDebug;
    }

}
