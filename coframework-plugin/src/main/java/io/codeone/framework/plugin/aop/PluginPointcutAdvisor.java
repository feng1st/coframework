package io.codeone.framework.plugin.aop;

import lombok.Getter;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Getter
public class PluginPointcutAdvisor extends AbstractPointcutAdvisor {

    @Autowired
    private PluginPointcut pointcut;

    @Autowired
    private PluginInterceptor advice;
}
