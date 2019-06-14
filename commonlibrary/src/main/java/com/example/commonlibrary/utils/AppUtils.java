package com.example.commonlibrary.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AppUtils {


    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图标 bitmap
     * @param context
     */
    public static synchronized Bitmap getBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    public static String getMetaDataApplication(Context context,String key) throws PackageManager.NameNotFoundException {
        //在application应用<meta-data>元素。
       ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(getPackageName(context),PackageManager.GET_META_DATA);
        return  appInfo.metaData.getString(key);
    }



    public static String getMetaDataActivity(Activity activity, String key) throws PackageManager.NameNotFoundException {
       //在Activity应用<meta-data>元素。
        ActivityInfo info = activity.getPackageManager()
                .getActivityInfo(activity.getComponentName(),PackageManager.GET_META_DATA);
        return   info.metaData.getString(key);
    }

    public static String getMetaDataService(Context context, String key,Class serviceClz) throws PackageManager.NameNotFoundException {
        //在Service应用<meta-data>元素。
      ComponentName cn = new ComponentName(context, serviceClz);
       ServiceInfo info = context.getPackageManager().getServiceInfo(cn, PackageManager.GET_META_DATA);
      return  info.metaData.getString(key);
    }


    public static String getMetaDataReceiver(Context context, String key,Class serviceClz) throws PackageManager.NameNotFoundException {
        //在Receiver应用<meta-data>元素。
        ComponentName cn = new ComponentName(context, serviceClz);
        ActivityInfo info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
        return  info.metaData.getString(key);
    }
}
