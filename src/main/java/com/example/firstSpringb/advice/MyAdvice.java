package com.example.firstSpringb.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {

    private Logger logger = LoggerFactory.getLogger(MyAdvice.class);

    /**
     * 定义切点
     */
    @Pointcut(value = "execution( * com.example.firstSpringb.controller.*.*(..))")
    public void myPointCut() {

    }

    /**
     * 使用环绕通知
     */
    @Around(value = "myPointCut()")
    public Object requestLogger(ProceedingJoinPoint pjp) throws Throwable {
        String clsaaName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        ObjectMapper objectMapper = new ObjectMapper();

        //调用目标方法之前操作
        logger.info("调用前-->类名：" + clsaaName + " 方法名：" + methodName + " 请求参数：" +  objectMapper.writeValueAsString(args));
        //调用目标方法
        Object proceed = pjp.proceed();
        //调用目标方法之后操作
        logger.info("调用后-->类名：" + clsaaName + " 方法名：" + methodName + " 返回值：" +  objectMapper.writeValueAsString(proceed));
        return proceed;
    }
}
