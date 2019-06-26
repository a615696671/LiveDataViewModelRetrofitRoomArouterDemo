package com.example.base.aspect.debugtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface DebugTraceTime {
    /* 点击间隔时间 */
    Class<?extends  AroundUtils > value() default TimeAround.class;
}