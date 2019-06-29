package com.example.common.httpexpand.progress;

import android.os.Handler;
import android.os.Looper;

import com.example.common.httpexpand.progress.OnDownloadListener;
import com.example.common.httpexpand.progress.ProgressInfo;

import java.io.IOException;

import okio.Buffer;
import okio.ForwardingSource;
import okio.Source;

//继承ForwardingSource 实现具体方法
public class WrapSource extends ForwardingSource {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Source mSource;
    private ProgressInfo mInfo;
    private OnDownloadListener mListener;
    private boolean mDoProgress;

    public WrapSource(Source source, ProgressInfo info, OnDownloadListener listener, boolean doProgress) {
        //传入源Source、进度信息、监听进度等信息。
        super(source);
        mSource = source;
        mInfo = info;
        mListener = listener;
        //传入是否继续执行回调boolean参数，如果之前执行有异常，则不再继续执行回调
        mDoProgress = doProgress;
    }

    @Override
    public long read(Buffer sink, long byteCount) throws IOException {
        //获取具体进度信息，来到了熟悉的具体IO
        long read = super.read(sink, byteCount);
        if (read != -1) {
            long l = mInfo.getCurrentLength() + read;
            mInfo.setCurrentLength(l);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mDoProgress) {
                        //切换到主线程，回调数据
                        mListener.onDownLoadProgress(mInfo);
                    }
                }
            });
        }
        return read;
    }
}