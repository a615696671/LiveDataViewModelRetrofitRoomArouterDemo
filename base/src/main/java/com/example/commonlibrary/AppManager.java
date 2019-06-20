package com.example.commonlibrary;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.example.commonlibrary.http.OkHttpClientHelper;
import com.example.commonlibrary.utils.LogUtils;

import java.util.Stack;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AppManager {
    private volatile  static  Stack<Activity> activityStack;
    private volatile  static AppManager instance;
    private static String TAG="AppManager";
    public  Stack<Activity> getActivityStack() {
        return activityStack;
    }

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                    LogUtils.d(TAG,"getAppManager()");
                }
            }
        }
        return instance;


    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            synchronized (AppManager.class) {
                if (activityStack == null) {
                    activityStack = new Stack<Activity>();
                }
            }
        }
        activityStack.add(activity);
        LogUtils.d(TAG,"addActivity()");
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        LogUtils.d(TAG,"currentActivity()");
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
            LogUtils.d(TAG,"finishActivity()");
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
            LogUtils.d(TAG,"finishActivity()");
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                LogUtils.d(TAG,"finishActivity()");
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                LogUtils.d(TAG,"finishAllActivity()");
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            LogUtils.d(TAG,"AppExit()");
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
