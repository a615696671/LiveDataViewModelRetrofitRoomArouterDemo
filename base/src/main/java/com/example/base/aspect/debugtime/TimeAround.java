package com.example.base.aspect.debugtime;

import android.util.Log;

public class TimeAround implements AroundUtils{
    private long startTime;
    private long lastTime;
    @Override
    public void startAround() {
        startTime=System.currentTimeMillis();
    }
    @Override
    public void stopAround() {
        lastTime=System.currentTimeMillis();
        Log.e("TimeAround method consumption time",(lastTime-startTime)+"ms");

    }
}
