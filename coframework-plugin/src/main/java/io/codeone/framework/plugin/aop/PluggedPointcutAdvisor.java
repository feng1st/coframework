package io.codeone.framework.plugin.aop;

import lombok.Getter;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Fuses {@link PluggedPointcut} with {@link PluggedMethodInterceptor}.
 *
 * <p>About {@code @Role(BeanDefinition.ROLE_INFRASTRUCTURE)}: Whether to
 * activate this advisor, is based on which {@code AutoProxyCreator} is being
 * used (please refer to {@link BeanFactoryAdvisorRetrievalHelper} for details).
 * When {@code aspectjweaver} package is imported, the spring-aop will select
 * the {@link AnnotationAwareAspectJAutoProxyCreator}, and in which this
 * advisor will be enabled unconditionally. Otherwise, spring-aop will use the
 * {@link InfrastructureAdvisorAutoProxyCreator}, and which will check if the
 * candidate advisor has the {@link BeanDefinition#ROLE_INFRASTRUCTURE} role to
 * be enabled. So we added {@code @Role(BeanDefinition.ROLE_INFRASTRUCTURE)} to
 * be compatible with both situations.
 */
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class PluggedPointcutAdvisor extends AbstractPointcutAdvisor {

    /**
     * Decides whether a method should be intercepted by plugins.
     */
    @Resource
    @Getter
    private PluggedPointcut pointcut;

    /**
     * Intercepts method invocation using plugins.
     */
    @Resource
    @Getter
    private PluggedMethodInterceptor advice;
}
