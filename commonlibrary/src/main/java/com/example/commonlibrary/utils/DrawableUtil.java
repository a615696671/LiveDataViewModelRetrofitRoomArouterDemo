package com.example.commonlibrary.utils;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * drawableLeft和drawableRight图标点击工具类
 */

public class DrawableUtil <T extends TextView> {
    private final int LEFT = 0;
    private final int RIGHT = 2;

    private OnDrawableListener listener;
    private T mTextView;

    public DrawableUtil(T t, OnDrawableListener l) {
        mTextView =  t;
        mTextView.setOnTouchListener(mOnTouchListener);
        listener = l;
    }

    public interface OnDrawableListener {
         void onLeft(View v, Drawable left);

         void onRight(View v, Drawable right);
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (listener != null) {
                        Drawable drawableLeft = mTextView.getCompoundDrawables()[LEFT];
                        if (drawableLeft != null && motionEvent.getRawX() <= (view.getLeft() + drawableLeft.getBounds().width())) {
                            listener.onLeft(view, drawableLeft);
                            return true;
                        }
                        Drawable drawableRight = mTextView.getCompoundDrawables()[RIGHT];
                        if (drawableRight != null && motionEvent.getRawX() >= (view.getRight() - drawableRight.getBounds().width())) {
                            listener.onRight(view, drawableRight);
                            return true;
                        }
                    }
                    break;
            }
            return false;
        }
    };
}
