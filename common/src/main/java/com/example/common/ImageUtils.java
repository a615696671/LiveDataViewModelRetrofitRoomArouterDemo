package com.example.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.base.R;

public class ImageUtils {
  public static void  loadImage(Context context,ImageView imageView,String imageUrl){
      RequestOptions  options =new RequestOptions();
      options .diskCacheStrategy(DiskCacheStrategy.ALL);
      Glide.with(context)
              .load(imageUrl)// 图片地址
              .apply(options) // 参数
              .into(imageView); // 需要显示的ImageView控件

  }
}
