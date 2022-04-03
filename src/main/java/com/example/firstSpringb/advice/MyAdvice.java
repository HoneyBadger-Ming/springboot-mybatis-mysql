package com.example.firstSpringb.advice;

import com.example.firstSpringb.pojo.Girl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;

@Component
@Aspect
public class MyAdvice {

    private Logger logger = LoggerFactory.getLogger(MyAdvice.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义切点1
     */
    @Pointcut(value = "execution( * com.example.firstSpringb.controller.GirlController.*(..))")
    public void myPointCut() {
    }

    /**
     * 定义切点2
     */
    @Pointcut(value = "execution( * com.example.firstSpringb.controller.HelloController.*(..))")
    public void myPointcut2(){}

    /**
     * 使用环绕通知
     */
//    @Around(value = "myPointCut()")
    public Object requestLogger(ProceedingJoinPoint pjp) throws Throwable {
        String clsaaName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        ObjectMapper objectMapper = new ObjectMapper();
        //调用目标方法之前操作
        logger.info("Around调用前-->类名：" + clsaaName + " 方法名：" + methodName + " 请求参数：" + objectMapper.writeValueAsString(args));
        //调用目标方法
        Object proceed = pjp.proceed();
        //调用目标方法之后操作
        logger.info("Around调用后-->类名：" + clsaaName + " 方法名：" + methodName + " 返回值：" + objectMapper.writeValueAsString(proceed));
        return proceed;
    }

    /**
     * before通知
     */
//    @Before(value = "myPointCut()")
    public void beforeAdvice(JoinPoint jp) throws JsonProcessingException {
        String className = jp.getTarget().getClass().getSimpleName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        ObjectMapper objectMapper = new ObjectMapper();
        //调用目标方法之前的操作
        logger.info("before-->类名： " + className + " 方法名： " + methodName + " 请求参数： " + objectMapper.writeValueAsString(args));
    }

    /**
     * 使用before通知，打印详细请求参数
     */
    @Before(value = "myPointCut() || myPointcut2()")
    public void beforeAdvice2(JoinPoint joinPoint) {
        logger.info("方法执行前.......before");
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("<============================Method Start=============================>");
        logger.info("请求URL: ===>" + request.getRequestURI());
        logger.info("请求方式: ===>" + request.getMethod());
        logger.info("请求方法: ===>" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("请求参数: ===>" + Arrays.toString(joinPoint.getArgs()));
        logger.info("-----------------------------------------------------------");
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 使用AfterReturning通知，打印详细响应数据
     */
    @AfterReturning(value = "myPointCut() || myPointcut2()", returning = "response")
    public void afterReturningAdvice2(List response){
        if (startTime.get() == null) {
            startTime.set(System.currentTimeMillis());
        }
        logger.info("耗时(毫秒): <==" + (System.currentTimeMillis() - startTime.get()));
        logger.info("返回数据: {}", response);
        logger.info("<============================Method End=============================>");
    }

    /**
     * After通知
     */
    @After(value = "myPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        ObjectMapper objectMapper = new ObjectMapper();
        //调用方法后的操作
        logger.info("After--》类名：" + className + "，方法名：" + methodName);
    }

    /**
     * AfterReturning
     */
//    @AfterReturning(pointcut = "myPointCut()", returning = "response")
    public void afterReturning(List<Girl> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(response);
        logger.info("AfterReturning--》" + s);
    }

    @AfterThrowing(pointcut = "myPointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        String name = joinPoint.getSignature().getName();
        logger.info("afterThrowing--> " + name + ex);
    }

}
