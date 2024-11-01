package org.entropy.shortchain.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.entropy.shortchain.event.ClickShortLinkEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ClickCountAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Pointcut("@annotation(org.entropy.shortchain.annotation.EnableStatistics)")
    public void statisticsPointcut() {
        // 空的方法，定义公共切点
    }

    @Before(value = "statisticsPointcut()")
    public void beforeStatistics(JoinPoint joinPoint) {
        String arg0 = (String) joinPoint.getArgs()[0];
        applicationEventPublisher.publishEvent(new ClickShortLinkEvent(arg0));
    }
}
