package com.example.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.base.R;

public class ImageUtils {
  public static void  loadImage(Context context,ImageView imageView,String imageUrl){
      RequestOptions  options =new RequestOptions();
//      options .placeholder(R.drawable.video_default).error(R.drawable.video_error)
      options .diskCacheStrategy(DiskCacheStrategy.ALL);
      Glide.with(context)
              .load(imageUrl) // 图片地址
              .apply(options) // 参数
              .into(imageView); // 需要显示的ImageView控件

  }
}
