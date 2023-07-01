package io.codeone.framework.plugin.aspect;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PluggedPointcutAdvisor extends AbstractPointcutAdvisor {

    @Resource
    private PluggedPointcut pointcut;

    @Resource
    private PluggedMethodInterceptor interceptor;

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return interceptor;
    }
}
