package com.example.ftpmodule;

import android.util.Log;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

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
}
