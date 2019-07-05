package com.example.testmodule;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.utils.AppUtils;
import com.example.common.ArouterConstant;
import com.example.base.aac.BaseDataBindFragment;
import com.example.testmodule.databinding.TestTestFragmentLayoutBinding;

@Route(path = ArouterConstant.TestFragment)
public class TestFragment extends BaseDataBindFragment<TestTestFragmentLayoutBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.test_test_fragment_layout;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     mView.findViewById(R.id.btn1).setOnClickListener(view1 -> ARouter.getInstance().build(ArouterConstant.CordovaMainActivity).navigation());
        mView.findViewById(R.id.btn).setOnClickListener(view12 -> ARouter.getInstance().build(ArouterConstant.TestActivity).navigation());
        mView.findViewById(R.id.btn2).setOnClickListener(view13 -> ARouter.getInstance().build(ArouterConstant.TestActivity3).navigation());


        mView.findViewById(R.id.btn3).setOnClickListener(view14 -> ARouter.getInstance().build(ArouterConstant.TestActivity2).navigation());
        mView.findViewById(R.id.btn4).setOnClickListener(view15 -> ARouter.getInstance().build(ArouterConstant.JpegTestActivity).navigation());
        mView.findViewById(R.id.btn5).setOnClickListener(view16 -> ARouter.getInstance().build(ArouterConstant.BassdiffTestActivity).navigation());
        TextView tv = mView.findViewById(R.id.tv);
        tv.setText(AppUtils.getVersionCode(mContext)+"");
    }

    @Override
    protected void initData() {


    }
}
