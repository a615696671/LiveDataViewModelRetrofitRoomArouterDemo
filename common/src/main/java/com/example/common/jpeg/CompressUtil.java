package com.example.common.jpeg;

import android.graphics.Bitmap;

public class CompressUtil {
    static {
        System.loadLibrary("jpeg-lib");
        System.loadLibrary("jpeg-1");
        System.loadLibrary("turbojpeg-1");
    }
    public native static int compressBitmap(Bitmap bitmap, int quality, String destFile);
}
