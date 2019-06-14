package com.example.commonlibrary.map;

import android.content.Context;

import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;


public class GetLocation {
    private OnLocationSuccess  l;

    public GetLocation setL(OnLocationSuccess l) {
        this.l = l;
        return this;
    }

    public  void getLoacation(final Context contex, final String cityName, String cityCode){
        GeocodeSearch geocodeSearch=new GeocodeSearch(contex);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i==1000){
                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null && geocodeResult.getGeocodeAddressList().size()>0){
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        Object[] objects={geocodeAddress.getLatLonPoint().getLatitude()+"",geocodeAddress.getLatLonPoint().getLongitude()+""};
                       if(l!=null){
                           l.getLocationSuccess(objects);
                           l.getLoacalCityCode(geocodeAddress.getAdcode());
                       }
                    }
                }else{
                    l.getLocationError();
                }
            }
        });
        //代表定位当前位置的精度 省 市  区
        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),cityCode);
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
//        DistrictSearch search = new DistrictSearch(contex);
//        DistrictSearchQuery query = new DistrictSearchQuery();
//        query.setKeywords(cityName);//传入关键字
//        query.setShowBoundary(false);//是否返回边界值
//        search.setQuery(query);
//        search.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
//            @Override
//            public void onDistrictSearched(DistrictResult districtResult) {
//                if (districtResult.getAMapException().getErrorCode() == 1000) {
//                    ArrayList<DistrictItem> district = districtResult.getDistrict();
//                    if (district!=null && district.size()>0){
//                        DistrictItem item = district.get(0);
//                        Object[] objects={item.getCenter().getLatitude()+"",item.getCenter().getLongitude()+""};
//                        l.getLoacal(objects,clas);
//                    }else{
//                        ToastUtils.show(contex,"获取地理位置失败", Toast.LENGTH_SHORT);
//                    }
//                }
//            }
//        });//绑定监听器
//        search.searchDistrictAnsy();//开始搜索
    }

    public interface   OnLocationSuccess{
        void getLocationSuccess(Object[] objects);
        void getLoacalCityCode(String cityCode);
       void  getLocationError();
    }

}
