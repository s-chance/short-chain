package org.entropy.shortchain.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.entropy.shortchain.annotation.EnableStatistics;
import org.entropy.shortchain.event.ClickShortLinkEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ClickCountAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Pointcut("@annotation(enableStatistics)")
    public void statisticsPointcut(EnableStatistics enableStatistics) {
        // 空的方法，定义公共切点
    }

    @Before(value = "statisticsPointcut(enableStatistics))", argNames = "joinPoint,enableStatistics")
    public void beforeStatistics(JoinPoint joinPoint, EnableStatistics enableStatistics) {
        String arg0 = (String) joinPoint.getArgs()[0];
        applicationEventPublisher.publishEvent(new ClickShortLinkEvent(arg0));
    }
}
