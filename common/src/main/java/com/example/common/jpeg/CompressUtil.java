package com.example.common.jpeg;

import android.graphics.Bitmap;
public class CompressUtil {
    static {
        System.loadLibrary("jpeg-lib");
    }
    //无论怎么压缩，file的大小小下去了，但是转换成bitmap大小又回去了。
    // 上传时候做图片压缩可以节省服务器空间大小。在jpeg图片压缩中好多代码都没有返回bitmap的原因也在于此。
    public native static int compressBitmap(Bitmap bitmap, int quality, String destFile);


}
