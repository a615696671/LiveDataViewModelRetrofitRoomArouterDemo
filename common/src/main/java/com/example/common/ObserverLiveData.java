package com.example.common;

import androidx.lifecycle.Observer;

import com.example.commonlibrary.ContentValue;

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
