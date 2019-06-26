package com.example.base.aspect.statistics;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class StatisticsInstrumentation {

    public static final String TAG = "Statistics";

    @Around("execution(@com.example.commonlibrary.aspect.statistics.Statistics * *(..)) && @annotation(statistics)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, Statistics statistics) throws Throwable {
        calculate(statistics);
        joinPoint.proceed();//执行原方法
    }
    private void calculate(Statistics statistics){
        if(statistics != null){
            Log.e(TAG,  statistics.function().getFunctionName());
        }
    }
}
