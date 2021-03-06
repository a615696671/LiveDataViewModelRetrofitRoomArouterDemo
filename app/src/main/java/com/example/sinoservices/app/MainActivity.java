package com.example.sinoservices.app;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.common.ArouterConstant;
import com.example.base.aac.BaseActivity;


@Route(path = ArouterConstant.MainActivity)
public class MainActivity extends BaseActivity  {

    FrameLayout mainContent;
    String[] bottomText;
    private MainViewModel mainViewModel;
    private Fragment  currentFragment;
    private int  currentFragmentIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContent=  findViewById(R.id.main_content);
        mainViewModel = get(MainViewModel.class);
        currentFragmentIndex=0;
        mainViewModel.getFragment(ArouterConstant.TestFragment,0);
        mainViewModel.getLoginMutableLiveData().observe(this, fragment -> {
            if (currentFragment != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    //先判断是否被add过
                    if (currentFragment != null) {
                        transaction.hide(currentFragment);
                    }
                    if (fragment != null) {
                        transaction.add(R.id.main_content, fragment).commitAllowingStateLoss();
                    }
                } else {
                    if (currentFragment != null) {
                        transaction.hide(currentFragment);
                    }
                    if (fragment != null) {
                        transaction.show(fragment).commitAllowingStateLoss();
                    }
                }
                currentFragment=fragment;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
