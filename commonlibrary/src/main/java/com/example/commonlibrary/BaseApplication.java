package com.example.commonlibrary;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application{
    private static  boolean isDebug=true;
    private static Context  context;
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
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public boolean  isDebug(){
        return  isDebug;
    }
}
