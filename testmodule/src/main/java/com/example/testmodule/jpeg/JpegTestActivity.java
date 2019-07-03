package com.example.testmodule.jpeg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.common.ArouterConstant;
import com.example.common.jpeg.CompressUtil;
import com.example.testmodule.R;
import java.io.IOException;
import java.io.InputStream;

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
        TextView sizeView = findViewById(R.id.tv);
        sizeView.setText(bitmap.getByteCount()+"");
    }

    private String getSaveLocation() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
public void compressNative(View view){
     String result = getSaveLocation() + "/compress.png";
     CompressUtil.compressBitmap(this.bitmap, qu, result);
}

}
