package com.example.common;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.utils.AppUtils;
import com.example.common.map.LocationService;
import com.example.base.ActivityLifeCallBack;
import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.base.widget.SmartRefreshLayoutDefaultSetting;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;


public class BaseApplication  extends TinkerApplication  {
    private static  boolean isDebug=true;
    private static Context  context;
    private LocationService locationService;

    protected BaseApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.common.BaseApplication",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context=this;
        MultiDex.install(this);
        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);

    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> interactor.execute(), MainThreadImpl.getInstance()) {
            @Override
            public void run() {
                SmartRefreshLayoutDefaultSetting.refreshLayoutDefaultSettingInit();
                registerActivityLifecycleCallbacks(ActivityLifeCallBack.getActivityLifeCallBack());
                if (isDebug()) {
                    ARouter.openLog();     // 打印日志  这两行必须写在init之前，否则这些配置在init过程中将无效
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//                    LeakCanary.install(BaseApplication.this);//内存泄漏检测
                    //性能优化工具之StrictMode
                    // ThreadPolicy（线程策略），主要可以检测主线程中的一些耗时操作。
                    //VmPolicy（虚拟机策略），主要可以检测一些对象的泄漏。
//                    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                            .detectAll()
//                            .penaltyLog()
//                            .penaltyFlashScreen()
//                            .build());
//                    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                            .detectAll()
//                            .penaltyLog()
//                            .build());
                }
                ARouter.init(BaseApplication.this); // 尽可能早，推荐在Application中初始化
                String bugly =  AppUtils.getMetaDataApplication(context,"bugly");
                Bugly.init(getApplicationContext(),bugly ,isDebug);
                bindService(new Intent(getContext(), BaseApplication.class), serviceConnection, Context.BIND_AUTO_CREATE);
                startService(new Intent(getContext(), LocationService.class));//开启定位服务
            }
        });
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
