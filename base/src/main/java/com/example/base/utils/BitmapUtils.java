package com.example.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

public class BitmapUtils {

    public static  Bitmap getBitmapFromPath(String absolutePath, int screenWidth, int screenHeight, boolean preferredConfig) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        // 这个isjustdecodebounds很重要
        opt.inJustDecodeBounds = true;
        Bitmap  bm = BitmapFactory.decodeFile(absolutePath, opt);
        // 获取到这个图片的原始宽度和高度
        int picWidth = opt.outWidth;
        int picHeight = opt.outHeight;
        // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
        opt.inSampleSize = 1;
        // 根据屏的大小和图片大小计算出缩放比例
        if (picWidth > picHeight) {
            if (picWidth > screenWidth)
                opt.inSampleSize = picWidth / screenWidth;
        } else {
            if (picHeight > screenHeight)

                opt.inSampleSize = picHeight / screenHeight;
        }
        // 这次再真正地生成一个有像素的，经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        //阻止大幅度拉升图像时导致的像banding(色带)这样的问题.Dither概念常用在数字声音和数字视频数据处理中，也常用在compact disc中。
        opt.inDither = true;
        if(preferredConfig){
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        bm= BitmapFactory.decodeFile(absolutePath, opt);
        return  bm;
    }

    public static  Bitmap getBitmapFromUri(Context context,Uri uri, int screenWidth, int screenHeight, boolean preferredConfig) throws IOException {
            InputStream input = context.getApplicationContext().getContentResolver().openInputStream(uri);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, opt);
            // 获取到这个图片的原始宽度和高度
            int picWidth = opt.outWidth;
            int picHeight = opt.outHeight;
            input = context.getContentResolver().openInputStream(uri);
            // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
            opt.inSampleSize = 1;
            // 根据屏的大小和图片大小计算出缩放比例
            if (picWidth > picHeight) {
                if (picWidth > screenWidth)
                    opt.inSampleSize = picWidth / screenWidth;
            } else {
                if (picHeight > screenHeight)
                    opt.inSampleSize = picHeight / screenHeight;
            }
            // 这次再真正地生成一个有像素的，经过缩放了的bitmap
            opt.inJustDecodeBounds = false;
            //阻止大幅度拉升图像时导致的像banding(色带)这样的问题.Dither概念常用在数字声音和数字视频数据处理中，也常用在compact disc中。
            opt.inDither = true;
            if(preferredConfig){
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, opt);
            if(input!=null){
                input.close();
            }
            return  bitmap;
    }

    public static  Bitmap getBitmapFromByte(byte[] imgByte, int screenWidth, int screenHeight, boolean preferredConfig) throws IOException {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, opt);
        // 获取到这个图片的原始宽度和高度
        int picWidth = opt.outWidth;
        int picHeight = opt.outHeight;
        input = new ByteArrayInputStream(imgByte);
        SoftReference<Bitmap> softRef = new SoftReference<>(BitmapFactory.decodeStream(
                input, null, opt));
        // isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
        opt.inSampleSize = 1;
        // 根据屏的大小和图片大小计算出缩放比例
        if (picWidth > picHeight) {
            if (picWidth > screenWidth)
                opt.inSampleSize = picWidth / screenWidth;
        } else {
            if (picHeight > screenHeight)
                opt.inSampleSize = picHeight / screenHeight;
        }
        // 这次再真正地生成一个有像素的，经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        //阻止大幅度拉升图像时导致的像banding(色带)这样的问题.Dither概念常用在数字声音和数字视频数据处理中，也常用在compact disc中。
        opt.inDither = true;
        if(preferredConfig){
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        bitmap = softRef.get();
        input.close();
        return  bitmap;
    }

}
