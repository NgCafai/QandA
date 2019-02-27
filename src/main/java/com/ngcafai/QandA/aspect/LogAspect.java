package com.ngcafai.QandA.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 第一个*代表返回值，第二个*代表类名，第三个*代表方法名，".."代表参数
    @Before("execution(* com.ngcafai.QandA.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null) {
                stringBuilder.append("arg:" + arg.toString() + "|");
            }
        }
        stringBuilder.append(joinPoint.getSignature().toString());
        logger.info(stringBuilder.toString());
    }

    @After("execution(* com.ngcafai.QandA.controller.*.*(..))")
    public void afterMethod() {
        logger.info("after method");
    }
}
