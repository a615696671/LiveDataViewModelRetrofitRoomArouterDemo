package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import com.example.commonlibrary.aac.BaseViewModel;
import com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo.main.IMainFragment;
import com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo.main.MainFragmentImpl;

public class MainViewModel extends BaseViewModel implements IMainFragment.CallBack {

    IMainFragment   iMainFragment;

    public MainViewModel() {
        this.iMainFragment = new MainFragmentImpl();
    }

    public MutableLiveData<Fragment> getLoginMutableLiveData(){
        return get(Fragment.class);
    }


    public void getFragment(String uri,int index){
        iMainFragment.createFragmentModel(uri,index,this);
    }

    public void refreshFragment(String uri,int index){
        iMainFragment.setFragmentRefresh(uri,index);
    }

    @Override
    public void onMainFragmentCreateSuccess(Fragment fragment) {
        getLoginMutableLiveData().setValue(fragment);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(iMainFragment!=null){
            iMainFragment.clear();
            iMainFragment=null;
        }
    }
}