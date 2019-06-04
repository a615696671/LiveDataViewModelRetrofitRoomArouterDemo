package com.example.commonlibrary.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.example.commonlibrary.AppManager;
import com.example.commonlibrary.R;

import java.lang.reflect.Field;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 创建日期：2017/11/24
 *
 * @author kong
 * @version 1.0
 * @since JDK 1.8
 * 文件名称 BaseActivity
 * 类说明：activity基类
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    private Toast toast;
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mPresent = createPresent();
        mPresent.attachView((V) this);
        mContext = this;
    }


    protected T mPresent;


    /**
     * 子类实现具体的构建过程
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
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.common_bg_translucent);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        progressDialog.setContentView(R.layout.common_loading_view_layout);
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
    protected void onStart() {
        super.onStart();
        initEvent();
    }

    public abstract void initEvent();

    @Override
    protected void onDestroy() {
        mPresent.detach();
        fixInputMethodManagerLeak(this);
        dismissProgressDialog();
        progressDialog = null;
        //将Activity实例从AppManager的堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }


    //Toast
    public void showToast(Context context,
                          String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    public void initStateBarHeight(View viewStatusBar) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = getStatusBarHeight();
        viewStatusBar.setLayoutParams(params);
    }

    public int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }

    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            if (getCurrentFocus() != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    /**
     * @param destContext 解决软键盘泄漏问题
     **/
    public void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环
                    }
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
