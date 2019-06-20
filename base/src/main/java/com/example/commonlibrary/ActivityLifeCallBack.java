package com.example.commonlibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.commonlibrary.utils.LogUtils;

public class ActivityLifeCallBack  implements Application.ActivityLifecycleCallbacks{
    private  volatile static  ActivityLifeCallBack  activityLifeCallBack=new ActivityLifeCallBack();
    private String TAG=this.getClass().getName();
    private  ActivityLifeCallBack() {

    }
    public static  ActivityLifeCallBack getActivityLifeCallBack (){
        if (activityLifeCallBack == null) {
            synchronized (ActivityLifeCallBack.class) {
                if (activityLifeCallBack == null) {
                    activityLifeCallBack = new ActivityLifeCallBack();
                }
            }
        }
        return activityLifeCallBack;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        //将Activity实例添加到AppManager的堆栈
        AppManager.getAppManager().addActivity(activity);
        LogUtils.d(TAG,"onActivityCreated()");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtils.d(TAG,"onActivityStarted()");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtils.d(TAG,"onActivityResumed()");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtils.d(TAG,"onActivityPaused()");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtils.d(TAG,"onActivityStopped()");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        LogUtils.d(TAG,"onActivitySaveInstanceState()");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.d(TAG,"onActivityDestroyed()");
        AppManager.getAppManager().finishActivity(activity);
    }
}
