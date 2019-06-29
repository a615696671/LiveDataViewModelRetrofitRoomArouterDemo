package com.example.common.httpexpand.progress;

public interface OnUploadListener {
    void onUploadGetContentLengthFail(ProgressInfo  progressInfo);
    void onUpLoadProgress(ProgressInfo  progressInfo);
}
