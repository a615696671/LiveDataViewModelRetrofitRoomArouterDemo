package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo.main;

import androidx.fragment.app.Fragment;

public interface IMainFragment {
    boolean createFragmentModel(String uri,int index, final IMainFragment.CallBack callBack);
    void setFragmentRefresh(String uri,int index);
    void clear();
    interface  CallBack{
        void onMainFragmentCreateSuccess(Fragment fragment);
    }
}
