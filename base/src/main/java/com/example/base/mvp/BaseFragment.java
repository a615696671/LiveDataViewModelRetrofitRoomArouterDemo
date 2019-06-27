package com.example.base.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.base.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * 创建日期：2018/3/8 0008 上午 9:58
 *
 * @author kong
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 BaseFragment
 * 类说明：Fragment基类
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    protected T mPresent;
    protected View mView;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = initView(inflater, container, savedInstanceState);
        return mView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresent = createPresent();
        mPresent.attachView((V) this);
        initView();
        initListener();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public abstract void initData();

    public abstract void initListener();

    public abstract void initView();

    @Override
    public void onDestroyView() {
        mPresent.detach();
        super.onDestroyView();
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 子类具体实现创建过程
     *
     * @return
     */
    protected abstract T createPresent();
    public ProgressDialog progressDialog;


    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, R.style.MyDialog);
        }
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.getWindow().setDimAmount(0);
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.base_bg_translucent);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        progressDialog.setContentView(R.layout.base_loading_view_layout);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        // progressDialog.setMessage("正在加载...");
    }


    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
    private Toast mToast;

    public void showToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }




    public int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }



    public void initStateBarHeight(View viewStatusBar) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = getStatusBarHeight();
        viewStatusBar.setLayoutParams(params);
    }
}
