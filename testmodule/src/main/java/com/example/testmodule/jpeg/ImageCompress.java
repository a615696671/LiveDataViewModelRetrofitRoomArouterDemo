package com.example.testmodule.jpeg;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class ImageCompress {
    private static int DEFAULT_QUALITY = 95;


    public static void compressBitmap(Bitmap bit, String fileName, boolean optimize) {
        saveBitmap(bit, DEFAULT_QUALITY, fileName, optimize);
    }

    public static void compressBitmap(Bitmap image, String filePath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 20;
        // JNI调用保存图片到SD卡 这个关键
        ImageCompress.saveBitmap(image, options, filePath, true);
    }

    public static void saveBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality, fileName.getBytes(), optimize);
    }

    public static native String compressBitmap(Bitmap bit, int w, int h, int quality, byte[] fileNameBytes,
                                               boolean optimize);

}
