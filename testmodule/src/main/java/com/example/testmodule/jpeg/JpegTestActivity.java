package com.example.testmodule.jpeg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.base.domain.executor.impl.ThreadExecutor;
import com.example.base.domain.interactors.base.AbstractInteractor;
import com.example.base.domain.threading.MainThreadImpl;
import com.example.common.ArouterConstant;
import com.example.common.jpeg.CompressUtil;
import com.example.testmodule.R;
import com.example.testmodule.bassdifftest.BassdiffTestActivity;

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
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        bitmap = BitmapFactory.decodeStream(open,null,opt);
        TextView sizeView = findViewById(R.id.tv);
        sizeView.setText(bitmap.getByteCount()+"");
    }

    private String getSaveLocation() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
public void compressNative(View view){
    ThreadExecutor.getInstance().execute(new AbstractInteractor(interactor -> {
        interactor.execute()  ;
        }, MainThreadImpl.getInstance()) {
        @Override
        public void run() {
            //开始压缩
            String result = getSaveLocation() + "/compress.png";
            CompressUtil.compressBitmap(bitmap, qu, result);
            mMainThread.post(() -> Toast.makeText(JpegTestActivity.this, "压缩成功!", Toast.LENGTH_SHORT).show());
        }
    });

 }
}
