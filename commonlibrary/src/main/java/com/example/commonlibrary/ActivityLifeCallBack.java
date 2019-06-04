package com.example.commonlibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class ActivityLifeCallBack  implements Application.ActivityLifecycleCallbacks{
    private  volatile static  ActivityLifeCallBack  activityLifeCallBack=new ActivityLifeCallBack();

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
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppManager.getAppManager().finishActivity(activity);
    }
}
