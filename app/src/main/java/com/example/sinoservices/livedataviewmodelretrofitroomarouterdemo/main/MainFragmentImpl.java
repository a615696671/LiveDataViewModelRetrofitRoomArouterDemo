package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo.main;

import androidx.fragment.app.Fragment;


import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;
import java.util.Map;

public class MainFragmentImpl implements IMainFragment {
    private Map<Integer, Fragment> mHashMap = new HashMap(16);
    public  Map getInstance() {
        if (mHashMap == null) {
            mHashMap = new HashMap<>(16);
        }
        return mHashMap;
    }
    @Override
    public boolean createFragmentModel(String uri,int index, CallBack callBack) {
        Fragment fragment;
        if (mHashMap == null) {
            getInstance();
        }
        if (mHashMap.get(index) == null) {
            fragment = (Fragment) ARouter.getInstance().build(uri).navigation();
            mHashMap.put(index, fragment);
        } else {
            fragment = mHashMap.get(index);
        }
        callBack.onMainFragmentCreateSuccess(fragment);
        return false;
    }

    @Override
    public void setFragmentRefresh(String uri,int index) {
        if (mHashMap == null) {
            getInstance();
        }
        if (mHashMap.get(index) != null) {
            mHashMap.remove(index);
        }
    }

    @Override
    public void clear() {
        if(mHashMap!=null){
            mHashMap.clear();
        }
        mHashMap=null;
    }
}
