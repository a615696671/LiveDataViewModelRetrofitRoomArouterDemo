package com.example.common.alibabaprovider;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ArouterConstant;
import com.example.common.ContentValue;
import com.example.common.map.LocationServiceBean;
import com.example.base.utils.SharedPreferencesUtils;
import com.google.gson.Gson;

@Route(path = ArouterConstant.ArouterCommonProviderImpl, name = "common数据获取类")
public class ArouterCommonProviderImpl implements ArouterCommonProvider{
    private   Context  context;

    @Override
    public LocationServiceBean getLocationData() {
        SharedPreferencesUtils preferencesUtils = SharedPreferencesUtils.getInstance(context);
        String value = preferencesUtils.getValue(ContentValue.LOCATION_DATA, "");
        if(!TextUtils.isEmpty(value)){
            return new Gson().newBuilder().serializeNulls().create().fromJson(value,LocationServiceBean.class);
        }
         return  null;
    }

    @Override
    public void init(Context context) {
       this.context=context;
    }
}
