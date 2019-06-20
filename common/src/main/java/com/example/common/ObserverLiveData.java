package com.example.common;

import androidx.lifecycle.Observer;

public abstract class ObserverLiveData<M extends BaseBean> implements Observer<M> {
    public abstract void onChangedSuccess(M model);
    public abstract void onChangedFailure(M  throwable);
    @Override
    public void onChanged(M o) {
        if(o.flag==0){
            onChangedSuccess(o);
        }else{
            onChangedFailure(o);
        }
    }
}
