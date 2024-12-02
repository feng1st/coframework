package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.binding.repo.MethodPluginBindingRepo;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Pointcut for identifying methods that should be intercepted by plugins.
 */
@Component
public class PluginPointcut extends StaticMethodMatcherPointcut {

    @Autowired
    private MethodPluginBindingRepo methodPluginBindingRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        try {
            Object.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return false;
        } catch (NoSuchMethodException ignored) {
        }

        return methodPluginBindingRepo.dynamicBind(method);
    }
}
