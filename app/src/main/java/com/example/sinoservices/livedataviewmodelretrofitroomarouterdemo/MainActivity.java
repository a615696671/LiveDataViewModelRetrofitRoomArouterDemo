package com.example.sinoservices.livedataviewmodelretrofitroomarouterdemo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.common.ArouterConstant;
import com.example.base.aac.BaseActivity;

//implements BottomNavigationBar.OnTabSelectedListener
@Route(path = ArouterConstant.MainActivity)
public class MainActivity extends BaseActivity
{
//    BottomNavigationBar bottomNavigationBar;
    FrameLayout mainContent;
    String[] bottomText;
    private MainViewModel mainViewModel;
    private Fragment  currentFragment;
    private int  currentFragmentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContent=  findViewById(R.id.main_content);
//        bottomNavigationBar=findViewById(R.id.bottom_navigation_bar);
//        bottomText = getResources().getStringArray(R.array.app_bottom_navigation_bar);
//        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
//        bottomNavigationBar
//                .addItem(new BottomNavigationItem(R.mipmap.app_icon, bottomText[0]).
//                        setInActiveColor(ContextCompat.getColor(mContext,R.color.colorPrimary))
//                        .setActiveColor(ContextCompat.getColor(mContext,R.color.colorPrimaryDark)).
//                                setInactiveIconResource(R.mipmap.app_icon))
//                .setFirstSelectedPosition(0)
//                .initialise();
//           bottomNavigationBar.setTabSelectedListener(this);
           mainViewModel = get(MainViewModel.class);
           currentFragmentIndex=0;
           mainViewModel.getFragment(ArouterConstant.TestFragment,0);
           mainViewModel.getLoginMutableLiveData().observe(this, new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
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

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }






//
//    @Override
//    public void onTabSelected(int position) {
//        switch (position) {
//            case 0:
//                mainViewModel.getFragment(ArouterConstant.TestFragment,0 );
//                break;
//            case 1:
//
//                break;
//            case 2:
//
//                break;
//            case 3:
//
//                break;
//        }
//        currentFragmentIndex = position;
//    }
//
//    @Override
//    public void onTabUnselected(int position) {
//
//    }
//
//    @Override
//    public void onTabReselected(int position) {
//
//    }

}
