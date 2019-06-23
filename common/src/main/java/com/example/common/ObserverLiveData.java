package com.example.common;

import androidx.lifecycle.Observer;

/**
 * 创建日期：2018/1/30 0030 下午 4:48
 * @author Eddy.Kang
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 ObserverLiveData
 * 类说明：ObserverLiveData implements Observer
 */
public abstract class ObserverLiveData<M extends BaseBean> implements Observer<M> {
    public abstract void onChangedSuccess(M model);
    public abstract void onChangedFailure(M  throwable);
    @Override
    public void onChanged(M o) {
        if(o.flag== ContentValue.FLAG_SUCCESS){
            onChangedSuccess(o);
        }else{
            onChangedFailure(o);
        }
    }
}
