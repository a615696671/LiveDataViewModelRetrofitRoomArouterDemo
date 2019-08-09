package com.example.common.httpexpand.progress;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

//继承ResponseBody实现具体方法
public class WrapResponseBody extends ResponseBody {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ResponseBody mResponseBody;
    private OnDownloadListener mListener;
    private ProgressInfo mInfo;
    private BufferedSource mBufferedSource;
    private boolean mDoProgress;
    //传入进度，以及监听对象
    public WrapResponseBody(ResponseBody responseBody, ProgressInfo info, OnDownloadListener listener) {
        mResponseBody = responseBody;
        mInfo = info;
        mListener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        //接口方法，返回类型
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {

        long contentLength = mResponseBody.contentLength();
        //gzip压缩格式会返回-1，目前处理是在请求头信息指定("Accept-Encoding","identity")表示不压缩
        if (contentLength == -1) {
            mDoProgress = false;
            mHandler.post(() -> {
                //切换线程，进行失败回调
                mListener.onDownLoadGetContentLengthFail(mInfo);
            });
        } else {
            mDoProgress = true;
        }
        return contentLength;
    }

    @Override
    public BufferedSource source() {
        //WrapSource（继承ForwardingSource，ForwardingSource实现了Source接口）
        if (mBufferedSource == null) {
            mInfo.setContentLength(contentLength());
            //传入参数，读取具体进度信息，并回调
            WrapSource wrapSource = new WrapSource(mResponseBody.source(), mInfo, mListener,mDoProgress);
            mBufferedSource = Okio.buffer(wrapSource);
        }
        return mBufferedSource;
    }
}



