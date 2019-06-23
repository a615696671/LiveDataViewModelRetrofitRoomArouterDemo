package com.example.commonlibrary.utils;


import android.content.Context;
import android.content.SharedPreferences;
/**
 * 创建日期：2018/1/30 0030 下午 5:05
 *
 * @author kong
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 SharedPreferencesUtils
 * 类说明：SharedPreferencesUtils工具类
 */
public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance;
    private SharedPreferences SP;

    private SharedPreferencesUtils(Context context) {
        SP = context.getSharedPreferences(getDefaultSharedPreferencesName(context),
                getDefaultSharedPreferencesMode());
    }
    //其实就是application 的packge name啦
    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return Context.MODE_PRIVATE;
    }

    public static SharedPreferencesUtils getInstance(Context  context) {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtils(context);
                }
            }
        }
        return instance;
    }
 public SharedPreferences.Editor getEditor() {
        return SP.edit();
 }

    public <T> boolean saveValue(String keyword, T value) {
        SharedPreferences.Editor editor = getEditor();
        if (value instanceof String) {
            editor.putString(keyword, (String) value).apply();
            return true;
        }
        if (value instanceof Integer) {
            editor.putInt(keyword, (Integer) value).apply();
            return true;
        }
        if (value instanceof Boolean) {
            editor.putBoolean(keyword, (Boolean) value).apply();
            return true;
        }
        if (value instanceof Long) {
            editor.putLong(keyword, (Long) value).apply();
            return true;
        }
        if (value instanceof Float) {
            editor.putFloat(keyword, (Float) value).apply();
            return true;
        }
        return false;
    }

    public <T> T getValue(String keyword, T t) {
        if (t instanceof String) {
            String str = SP.getString(keyword, (String) t);
            t = (T) str;
        } else if (t instanceof Integer) {
            Integer in = SP.getInt(keyword, (Integer) t);
            t = (T) in;
        } else if (t instanceof Long) {
            Long lon = SP.getLong(keyword, (Long) t);
            t = (T) lon;
        } else if (t instanceof Float) {
            Float fl = SP.getFloat(keyword, (Float) t);
            t = (T) fl;
        } else if (t instanceof Boolean) {
            Boolean bl = SP.getBoolean(keyword, (Boolean) t);
            t = (T) bl;
        }
        return t;
    }
}
