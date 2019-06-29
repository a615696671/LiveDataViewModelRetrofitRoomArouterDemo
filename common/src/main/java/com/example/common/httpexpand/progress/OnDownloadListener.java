package com.example.common.httpexpand.progress;

public interface OnDownloadListener {
    void onDownLoadGetContentLengthFail(ProgressInfo progressInfo);
    void onDownLoadProgress(ProgressInfo progressInfo);
}
