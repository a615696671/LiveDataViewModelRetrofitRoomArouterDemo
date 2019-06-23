package com.example.common.alibabaprovider;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.example.common.map.LocationServiceBean;

public interface ArouterCommonEditProvider extends IProvider {
     boolean saveLocationData(LocationServiceBean locationServiceBean);
}
