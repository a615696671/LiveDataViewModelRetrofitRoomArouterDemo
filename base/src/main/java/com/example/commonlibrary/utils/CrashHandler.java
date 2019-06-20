package com.example.commonlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.example.commonlibrary.domain.executor.Executor;
import com.example.commonlibrary.domain.executor.impl.ThreadExecutor;
import com.example.commonlibrary.domain.interactors.base.AbstractInteractor;
import com.example.commonlibrary.domain.threading.MainThreadImpl;


import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;


/**
 * @Description:
 * @Author: Administrator
 * @Time: 2018/2/1 0001
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //文件路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() +File.separator+ "xwCrash";
    private static  String FILE_NAME;
    private static final String FILE_NAME_SUFEIX = ".log";
    public static final String TAG = "CrashHandler";
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap(16);

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        FILE_NAME=AppUtils.getAppName(mContext);
//        nameString = BmobUserManager.getInstance(mContext).getCurrentUserName();
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : " +e.toString());
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //WonderMapApplication.getInstance().getSpUtil().setCrashLog(true);// 每次进入应用检查，是否有log，有则上传
        // 使用Toast来显示异常信息
        ThreadExecutor.getInstance().execute(new AbstractInteractor(new Executor() {
            @Override
            public void execute(AbstractInteractor interactor) {
                interactor.execute();
            }
        }, MainThreadImpl.getInstance()) {
            @Override
            public void run() {

            }
        });
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info "+e.toString());
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.e(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info "+e.toString());
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称,便于将文件传送到服务器
     */
    private void saveCrashInfo2File(Throwable ex) {
        //如果没有SD卡，直接返回
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File filedir = new File(PATH);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currenttime));
        File exfile = new File(PATH +File.separator+FILE_NAME+time + FILE_NAME_SUFEIX);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("错误日志文件路径",""+exfile.getAbsolutePath());
        pw.println(time);
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try{
            //当前版本号
            pw.println("App Version:" + pi.versionName + "_" + pi.versionCode);
            //当前系统
            pw.println("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
            //制造商
            pw.println("Vendor:" + Build.MANUFACTURER);
            //手机型号
            pw.println("Model:" + Build.MODEL);
            //CPU架构
            pw.println("CPU ABI:" + Build.CPU_ABI);
            ex.printStackTrace(pw);
        }catch (Exception e){

        }finally {
            if(pw!=null){
                pw.close();
            }
        }

        return ;
    }
}
