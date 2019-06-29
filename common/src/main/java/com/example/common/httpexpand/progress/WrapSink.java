package com.example.common.httpexpand.progress;

import android.os.Handler;
import android.os.Looper;

import com.example.common.httpexpand.progress.OnUploadListener;
import com.example.common.httpexpand.progress.ProgressInfo;

import java.io.IOException;

import okio.Buffer;
import okio.ForwardingSink;
import okio.Sink;

//继承ForwardingSink 实现具体方法
public class WrapSink extends ForwardingSink {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public OnUploadListener mListener;
    public ProgressInfo mInfo;
    public boolean mDoProgress;

    public WrapSink(Sink delegate, ProgressInfo info, OnUploadListener listener, boolean doProgress) {
        //传入源Source、进度信息、监听进度等信息。
        super(delegate);
        mInfo = info;
        mListener = listener;
        //传入是否继续执行回调boolean参数，如果之前执行有异常，则不再继续执行回调
        mDoProgress = doProgress;
    }

    @Override
    public void write(Buffer source, long byteCount) throws IOException {
        super.write(source, byteCount);
        //获取具体进度信息，来到了熟悉的具体IO
        long l = mInfo.getCurrentLength() + byteCount;
        mInfo.setCurrentLength(l);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDoProgress) {
                    //切换到主线程，回调数据
                    mListener.onUpLoadProgress(mInfo);
                }
            }
        });
    }
}