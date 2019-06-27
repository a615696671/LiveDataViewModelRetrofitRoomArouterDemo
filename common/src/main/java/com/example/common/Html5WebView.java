package com.example.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;




public class Html5WebView extends WebView {
    private ProgressBar progressView;//进度条
    private Context context;

    public ProgressBar getProgressView() {
        return progressView;
    }

    public Html5WebView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public Html5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }




    private void init() {
        //初始化进度条
        View view = LayoutInflater.from(context).inflate(R.layout.common_progress_layout_horizontal, null);
        progressView=view.findViewById(R.id.progressView);
        //把进度条加到Webview中
        addView(view);
    }

}
