package com.example.commonlibrary.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
public class FitterUtils {
    /**
     *
     *Android8.0 新特性
     * manifest里同时设置android:screenOrientation="portrait"
     * android:windowIsTranslucent=“true”可出现崩溃
     *
     * **/
    public  static  void  setRequestedOrientation(Activity activity){
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
    }

}
