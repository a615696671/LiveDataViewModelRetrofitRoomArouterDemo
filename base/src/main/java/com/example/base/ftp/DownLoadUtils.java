package com.example.base.ftp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DownLoadUtils {
    DownLoadListener mDownLoadListener;

    public void setmDownLoadListener(DownLoadListener mDownLoadListener) {
        this.mDownLoadListener = mDownLoadListener;
    }

    public void installApk(final Context context) {
                        final String[] ftpKeyArray = checkData(context);
                        FileUtil.createFile("version.txt");
                        File ftp = FTPUtils.downloadFile(ftpKeyArray[0], Integer.parseInt(ftpKeyArray[1]), ftpKeyArray[2], ftpKeyArray[3], FileUtil.getSDCardPath(), "/version.txt");
                        StringBuffer stringBuffer = new StringBuffer();
                        String line = null;
                        try {
                            InputStream instream = new FileInputStream(ftp);
                            InputStreamReader inputreader = new InputStreamReader(instream);
                            BufferedReader buffreader = new BufferedReader(inputreader);
                            //分行读取
                            while ((line = buffreader.readLine()) != null) {
                                stringBuffer.append(line);
                            }
                            instream.close();
                            int versionCode =getVersionCode(context.getApplicationContext());
                            String vserionStri = stringBuffer.toString().replace(" ", "");
                            String substring = vserionStri.substring(vserionStri.lastIndexOf("=") + 1, vserionStri.length());
                            try {
                                if (versionCode < Integer.parseInt(substring.trim())) {
                                    downLoadApk(context);
                                }
                            } catch (Exception e) {

                            }
                        } catch (Exception e) {
                        }

    }


    private void downLoadApk(final Context mContetx) {
        final String[] ftpKeyArray = checkData(mContetx);
        FileUtil.createFile(getAppName(mContetx)+".apk");
        File ftp = FTPUtils.downloadFile(ftpKeyArray[0], Integer.parseInt(ftpKeyArray[1]), ftpKeyArray[2], ftpKeyArray[3], FileUtil.getSDCardPath(), "/"+getAppName(mContetx)+".apk", mDownLoadListener);
        install(ftp,mContetx);
    }

    private void install(File filepath, Context context) {
        if (!filepath.exists()) {
            Toast.makeText(context, "没有找到要下载的安装包...", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(context,getPackageName(context)+".fileprovider",filepath);
            intent.setDataAndType(uri,"application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.parse("file://" + filepath.getAbsolutePath()),"application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    private String[] checkData(Context mContext){
        String ftpKey = (String) mContext.getApplicationInfo().metaData.get("ftpKey");
        if(TextUtils.isEmpty(ftpKey)){
            throw  new Resources.NotFoundException("manifest not found meta-data  key is ftpKey,check your manifest  is add meta-data");
        }
        final String[] ftpKeyArray = ftpKey.split("-");
        if(ftpKeyArray.length<4){
            throw  new IllegalArgumentException("manifest meta-data ftpKey is IllegalArgument. example is address-port-username-password");
        }
        return  ftpKeyArray;
    }
    public interface DownLoadListener {
        void onDownLoadListener(int progress);
    }
    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    private static synchronized int getVersionCode(Context context) {
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
    private static synchronized String getPackageName(Context context) {
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
     * 获取应用程序名称
     */
    private static synchronized String getAppName(Context context) {
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

}
