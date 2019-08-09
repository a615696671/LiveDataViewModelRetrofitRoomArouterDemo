package com.example.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.common.glide.ProgressInterceptor;
import com.example.common.glide.ProgressListener;

public class ImageUtils {
  public static void  loadImage(Context context,ImageView imageView,String imageUrl){

      //开始监听某个url


      ProgressInterceptor.addListener(imageUrl, new ProgressListener() {
          @Override
          public void onProgress(int progress) {
             Log.e(" ImageView===>",progress+"");
          }
      });


      RequestOptions options = new RequestOptions()
              .skipMemoryCache(true)
              .diskCacheStrategy(DiskCacheStrategy.NONE);

      //开始展示
      Glide.with(context).load(imageUrl).listener(new RequestListener<Drawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
              //加载失败 移除监听
//              ProgressInterceptor.removeListener(imageUrl);
              return false;
          }
          @Override
          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
              //成功 移除监听
//              ProgressInterceptor.removeListener(imageUrl);
              return false;
          }
      }).apply(options).into(imageView);
  }
}
