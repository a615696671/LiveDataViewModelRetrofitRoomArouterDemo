package com.example.common.jpeg;

import android.graphics.Bitmap;

public class CompressUtil {
    static {
        System.loadLibrary("jpeg-lib");
    }
    public native static int compressBitmap(Bitmap bitmap, int quality, String destFile);
}
