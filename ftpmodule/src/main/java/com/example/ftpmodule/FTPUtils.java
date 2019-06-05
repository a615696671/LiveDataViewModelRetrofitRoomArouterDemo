package com.example.ftpmodule;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class FTPUtils {
    public static File downloadFile(String url, int port, String username,
                                    String password, String localPath, String serverPath, final DownLoadUtils.DownLoadListener mDownLoadListener) {
        FTPClient ftpClient = new FTPClient();
        File localFile = null;
        try {
            ftpClient.setControlEncoding("utf-8");
            ftpClient.connect(url, port);
            ftpClient.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();//未连接到FTP，用户名或密码错误
                Log.e("FTP","连接失败！");
                return null;
            }
            Log.e("FTP","连接成功！");
            // 先判断服务器文件是否存在  
            FTPFile[] files = ftpClient.listFiles(serverPath);
            if (files.length == 0) {
                return null;
            }
            localPath = localPath + files[0].getName();
            final long serverSize = files[0].getSize()/100; // 获取远程文件的长度  
            localFile = new File(localPath);
            long localSize = 0;
            if (localFile.exists()) {
                localFile.delete();
            }
             // 开始准备下载文件  
            ftpClient.enterLocalActiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            OutputStream out = new FileOutputStream(localFile, true);
            ftpClient.setRestartOffset(0); //设置从哪里开始下，就是断点下载 
            InputStream input = ftpClient.retrieveFileStream(serverPath);
            byte[] b = new byte[1024];
            int length = 0;
            while ((length = input.read(b)) != -1) {
                out.write(b, 0, length);
                localSize+=length;
                if(mDownLoadListener!=null){
                    mDownLoadListener.onDownLoadListener((int)(localSize/serverSize));
                }
            }
            out.flush();
            out.close();
            input.close();
            // 此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉  
            if (ftpClient.completePendingCommand()) {
                Log.e("FTPUtils", "文件下载成功");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localFile;
    }
    public static File downloadFile(String url, int port, String username,
                                    String password, String localPath, String serverPath) {
        FTPClient ftpClient = new FTPClient();
        File localFile = null;
        try {
            ftpClient.setControlEncoding("utf-8");
            ftpClient.connect(url, port);
            ftpClient.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();//未连接到FTP，用户名或密码错误
                Log.e("FTP","连接失败！");
                return null;
            }
            Log.e("FTP","连接成功！");
            // 先判断服务器文件是否存在  
            FTPFile[] files = ftpClient.listFiles(serverPath);
            if (files.length == 0) {
                return null;
            }

            localPath = localPath + files[0].getName();
            localFile = new File(localPath);
            long localSize = 0;
            if (localFile.exists()) {
                localFile.delete();
            }
            // 开始准备下载文件  
            ftpClient.enterLocalActiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            OutputStream out = new FileOutputStream(localFile, true);
            ftpClient.setRestartOffset(localSize); //设置从哪里开始下，就是断点下载 
            InputStream input = ftpClient.retrieveFileStream(serverPath);
            byte[] b = new byte[1024];
            int length = 0;
            while ((length = input.read(b)) != -1) {
                out.write(b, 0, length);
            }
            out.flush();
            out.close();
            input.close();
            // 此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉  
            if (ftpClient.completePendingCommand()) {
                Log.e("FTPUtils", "文件下载成功");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localFile;
    }



    /***
     * 上传Ftp文件
     * @param localFile 当地文件
     *
     * */
    public boolean uploadFile(File localFile, String path, Context mContext) throws  Exception {
        String[] ftpKey = checkData(mContext);
        boolean success=false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpKey[0],Integer.parseInt(ftpKey[1]));
        ftpClient.login(ftpKey[2], ftpKey[3]);
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.disconnect();//未连接到FTP，用户名或密码错误
            throw  new Exception();
        }
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        String[] split = path.split("-");
        ftpClient.changeWorkingDirectory("FTP/");
        for (int i = 0; i <split.length-2; i++) {
            if(ftpClient.changeWorkingDirectory(split[i]+"/")){
                //切换到目录成功则表示有该文件夹存在
            }else{
                //切换到目录失败则表示有该文件夹不存在，则创建并切换到该目录
                ftpClient.makeDirectory(split[i]);
                ftpClient.changeWorkingDirectory(split[i]+ "/");
            }
        }
        String fileName=split[split.length-2]+split[split.length-1];
        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(localFile));
        success = ftpClient.storeFile(fileName+"_"+localFile.getName(), inStream);
        if(inStream!=null){
            inStream.close();
        }
        ftpClient.logout();
        if(!success){
            throw  new Exception("上传失败!");
        }
        return success;
    }

    public List<File> downloadFile(String localPath, String name,Context mContext){
        String[] ftpKey = checkData(mContext);
        List<File>  mFileList=new ArrayList<>();
        if(localPath.length()>=12){
            String year = localPath.substring(0, 4);
            String moth = localPath.substring(4, 6);
            String day = localPath.substring(6, 8);
            String hours = localPath.substring(8, 10);
            String min = localPath.substring(10, 12);
            String xx= localPath.substring(localPath.length()-5, localPath.length());
            String servicePath=year+"/"+moth+"/"+day+"/"+hours+"/"+min;

            FTPClient ftpClient = new FTPClient();
            File localFile = null;
            try {
                ftpClient.setControlEncoding("utf-8");
                ftpClient.connect(ftpKey[0], Integer.parseInt(ftpKey[1]));
                ftpClient.login(ftpKey[2], ftpKey[3]);
                if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                    ftpClient.disconnect();//未连接到FTP，用户名或密码错误
                    return mFileList;
                }
                ftpClient.changeWorkingDirectory(servicePath+"/");
                // 先判断服务器文件是否存在  
                FTPFile[] files = ftpClient.listFiles();
                if (files.length == 0) {
                    return mFileList;
                }
                FileUtil.createSDDir("JiaXT/");
                for (int i = 0; i <files.length ; i++) {
                    String  fileName = files[i].getName();
                    if(fileName.equals(xx+"_"+name)){
                        Log.e("fileName",fileName);
                        // long serverSize = files[0].getSize(); // 获取远程文件的长度
                        localFile= new File(FileUtil.getSDCardPath()+ "JiaXT/"+fileName);
                        long localSize = 0;
                        // 开始准备下载文件  
                        ftpClient.enterLocalActiveMode();
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        OutputStream out = new FileOutputStream(localFile, true);
                        ftpClient.setRestartOffset(localSize); //设置从哪里开始下，就是断点下载 
                        InputStream input = ftpClient.retrieveFileStream(files[i].getName());
                        byte[] b = new byte[1024];
                        int length = 0;
                        while ((length = input.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                        out.flush();
                        out.close();
                        input.close();
                        // 此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉  
                        if (ftpClient.completePendingCommand()) {
                            Log.e("FTPUtils", "文件下载成功");
                        }
                        ftpClient.logout();
                        if(localFile!=null&&localFile.length()>0){
                            mFileList.add(localFile);
                        }
                    }
                }

            } catch (SocketException e) {
                e.printStackTrace();
                Log.e("downloadFile",e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("downloadFile",e.getMessage());
            }
        }
        return mFileList;
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
}
