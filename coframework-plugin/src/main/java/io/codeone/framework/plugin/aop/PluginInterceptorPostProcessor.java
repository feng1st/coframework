package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.PluginBindingProcessor;
import io.codeone.framework.plugin.binding.repo.MethodPluginBindingRepo;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Post-processor for handling plugin bindings after method binding resolution.
 */
@Component
public class PluginInterceptorPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MethodPluginBindingRepo methodPluginBindingRepo;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PluginInterceptorPostProcessor) {
            return bean;
        }

        if (!AopUtils.isAopProxy(bean)) {
            return bean;
        }
        Class<?> targetClass = AopUtils.getTargetClass(bean);

        for (Method method : targetClass.getMethods()) {
            Set<Class<? extends Plugin>> pluginClasses = methodPluginBindingRepo.getPluginClasses(method);
            if (CollectionUtils.isEmpty(pluginClasses)) {
                continue;
            }
            for (Class<? extends Plugin> pluginClass : pluginClasses) {
                Plugin plugin = applicationContext.getBean(pluginClass);

                if (plugin instanceof PluginBindingProcessor) {
                    ((PluginBindingProcessor) plugin).processAfterBinding(method, targetClass);
                }
            }
        }

        return bean;
    }
}
