package io.codeone.framework.plugin.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * About the "@Role(BeanDefinition.ROLE_INFRASTRUCTURE)":
 * <p>
 * Whether to activate this advisor, is based on which "AutoProxyCreator" is
 * being used (please refer to the {@link BeanFactoryAdvisorRetrievalHelper}
 * for details).
 * <p>
 * When the "aspectjweaver" dependency is imported, the spring-aop will select
 * the {@link AnnotationAwareAspectJAutoProxyCreator}, and in which this
 * advisor will be enabled unconditionally.
 * <p>
 * Otherwise, spring-aop will use the
 * {@link InfrastructureAdvisorAutoProxyCreator}, and which will check if the
 * candidate advisor has the {@link BeanDefinition#ROLE_INFRASTRUCTURE} role to
 * be enabled.
 * <p>
 * So we added the @Role to be compatible with both situations.
 * <p>
 * Also, we did not import the "aspectjweaver" dependency since there is no
 * performance benefit.
 */
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
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
