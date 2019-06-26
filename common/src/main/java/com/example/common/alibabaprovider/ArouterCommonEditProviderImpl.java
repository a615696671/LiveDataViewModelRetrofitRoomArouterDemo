package com.example.common.alibabaprovider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ArouterConstant;
import com.example.common.ContentValue;
import com.example.common.map.LocationServiceBean;
import com.example.base.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Route(path = ArouterConstant.ArouterCommonEditProviderImpl, name = "common数据编辑类")
public class ArouterCommonEditProviderImpl  implements  ArouterCommonEditProvider {
    private   Context  context;
    @Override
    public boolean saveLocationData(LocationServiceBean locationServiceBean) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return SharedPreferencesUtils.getInstance(context).saveValue(ContentValue.LOCATION_DATA,gson.toJson(locationServiceBean));
    }

    @Override
    public void init(Context context) {
        this.context=context;
    }
}
