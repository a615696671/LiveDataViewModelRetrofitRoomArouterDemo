package com.example.commonlibrary.aspect.debugtime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class TimeTraceAspect {
    private static final String POINTCUT_METHOD =
            "execution(@com.example.commonlibrary.debugtime.DebugTraceTime * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.example.commonlibrary.debugtime.DebugTraceTime *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(DebugTraceTime.class)) {
            return null;
        }
        DebugTraceTime debugTraceTime = method.getAnnotation(DebugTraceTime.class);
        Class<? extends AroundUtils> around = debugTraceTime.value();
        AroundUtils aroundUtils = around.newInstance();
        aroundUtils.startAround();
        // 被注解的方法在这一行代码被执行
        Object result = joinPoint.proceed();
        aroundUtils.stopAround();
        return result;
    }
}
