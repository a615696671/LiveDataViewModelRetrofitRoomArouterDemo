package com.example.common.httpexpand.progress;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;

//继承ResquestBody实现具体方法
public class WrapRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private OnUploadListener mListener;
    private ProgressInfo mInfo;
    private boolean mDoProgress;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    //传入进度，以及监听对象
    public WrapRequestBody(RequestBody requestBody, ProgressInfo info, OnUploadListener listener) {
        mRequestBody = requestBody;
        mListener = listener;
        mInfo = info;
    }


    @Override
    public MediaType contentType() {
        //接口方法，返回类型
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            //上传内容长度，有异常走failWrok处理
            long l = mRequestBody.contentLength();
            mDoProgress = true;
            return l;
        } catch (IOException e) {
            e.printStackTrace();
            failWork();
            return -1;
        }
    }
    //进行失败处理
    private void failWork() {
        mDoProgress = false;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //切换线程，回调失败信息
                mListener.onUploadGetContentLengthFail(mInfo);
            }
        });
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mInfo.setContentLength(contentLength());
        // WrapSink （继承ForwardingSink，ForwardingSink实现了Sink接口）
        ///传入参数，读取具体进度信息，并回调
        WrapSink wrapSink = new WrapSink(sink, mInfo, mListener, mDoProgress);
        BufferedSink buffer = Okio.buffer(wrapSink);
        mRequestBody.writeTo(buffer);
        buffer.flush();
    }
}