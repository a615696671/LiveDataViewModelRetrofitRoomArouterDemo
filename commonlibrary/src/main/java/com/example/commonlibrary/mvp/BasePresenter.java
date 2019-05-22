package com.example.commonlibrary.mvp;

import java.lang.ref.WeakReference;

/**
 * 创建日期：2018/3/5
 *
 * @author kong
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 BasePresenter
 * 类说明：Presenter基类
 */
public abstract class BasePresenter<T> {
    protected WeakReference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    public void detach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
