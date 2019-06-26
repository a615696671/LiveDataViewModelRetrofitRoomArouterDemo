package com.example.base.utils;

public interface IArithmetic {
    int ARITHMETIC_SCALE0=0;
    int ARITHMETIC_SCALE1=1;
    int ARITHMETIC_SCALE2=2;
    int ARITHMETIC_SCALE3=3;
    int ARITHMETI_SCALE4=4;
    int ARITHMETIC_SCALE5=5;
    int ARITHMETIC_SCALE6=6;
    int ARITHMETIC_SCALE7=7;
    int ARITHMETIC_SCALE8=8;
    int ARITHMETIC_SCALE9=9;
    int ARITHMETIC_SCALE10=10;
    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
      double add(String v1,String v2,int scale);

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
      double sub(String v1,String v2,int scale);
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
      double mul(String v1,String v2,int scale);

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
      double div(String v1,String v2);

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
      double div(String v1,String v2,int scale);

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
      double round(String v,int scale);
}
