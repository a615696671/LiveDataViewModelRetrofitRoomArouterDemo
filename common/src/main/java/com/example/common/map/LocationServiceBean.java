package com.example.common.map;


import java.io.Serializable;
import java.util.Map;

public class LocationServiceBean implements Serializable {
    String  District, AoiName, longitude, Address, currentCity, currentPosition, latitude,pro,adCode;
    String cityCode;
    private Map<String,String>  map;//预留数据存储

    public Map<String,String> getMap() {
        return map;
    }

    public void setMap(Map<String,String> map) {
        this.map = map;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAoiName() {
        return AoiName;
    }

    public void setAoiName(String aoiName) {
        AoiName = aoiName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LocationServiceBean{" +
                "District='" + District + '\'' +
                ", AoiName='" + AoiName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", Address='" + Address + '\'' +
                ", currentCity='" + currentCity + '\'' +
                ", currentPosition='" + currentPosition + '\'' +
                ", latitude='" + latitude + '\'' +
                ", pro='" + pro + '\'' +
                ", adCode='" + adCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", map=" + map +
                '}';
    }
}
