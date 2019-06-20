package com.example.commonlibrary.utils;

import java.math.BigDecimal;

public class ArithmeticDouble implements IArithmetic {

    @Override
    public double add(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return round(Double.toString(b1.subtract(b2).doubleValue()),scale);
    }

    @Override
    public double sub(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return round(Double.toString(b1.subtract(b2).doubleValue()),scale);
    }

    @Override
    public double mul(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return round(Double.toString(b1.multiply(b2).doubleValue()),scale);
    }

    @Override
    public double div(String v1, String v2) {
        return div(v1,v2,IArithmetic.ARITHMETIC_SCALE10);
    }

    @Override
    public double div(String v1, String v2, int scale) {
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public double round(String v, int scale) {
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
