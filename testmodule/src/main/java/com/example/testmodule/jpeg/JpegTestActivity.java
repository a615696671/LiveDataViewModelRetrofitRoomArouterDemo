package com.example.testmodule.jpeg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.base.jpeg.CompressUtil;
import com.example.common.ArouterConstant;
import com.example.testmodule.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Route(path = ArouterConstant.JpegTestActivity)
public class JpegTestActivity extends AppCompatActivity {
    private int qu = 40;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpeg_test);
        AssetManager manager = getResources().getAssets();
        InputStream open = null; //得到输出流
        try {
            open = manager.open("max_image.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(open);
    }
    private void compressByDefault(Bitmap bitmap, int quality) {
        File file = new File(getSaveLocation() + "/compress2.png");
        if (file.exists()) {
            try {
                file.delete();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private String getSaveLocation() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public void compressNative(View view) {
        String result = getSaveLocation() + "/compress.png";
        Log.e("C_TAG", result);
        long time = System.currentTimeMillis();
        int i = CompressUtil.compressBitmap(bitmap, qu, result);
        Log.e("C_TAG", "Native" + (System.currentTimeMillis() - time));
        if (i == 1) {
            Toast.makeText(this, "压缩完成，耗时：" + (System.currentTimeMillis() - time) + "毫秒", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "压缩失败", Toast.LENGTH_LONG).show();
        }
    }

    public void compressSystem(View view) {
        long time = System.currentTimeMillis();
        compressByDefault(bitmap, qu);
        Log.e("C_TAG", "Java" + (System.currentTimeMillis() - time));
        Toast.makeText(this, "压缩完成，耗时：" + (System.currentTimeMillis() - time) + "毫秒", Toast.LENGTH_LONG).show();
    }
}
