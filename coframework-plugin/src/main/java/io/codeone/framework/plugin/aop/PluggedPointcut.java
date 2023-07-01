package io.codeone.framework.plugin.aop;

import io.codeone.framework.plugin.plug.Pluggers;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Component
public class PluggedPointcut extends StaticMethodMatcherPointcut {

    @Resource
    private Pluggers pluggers;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        try {
            Object.class.getDeclaredMethod(method.getName(),
                    method.getParameterTypes());
            return false;
        } catch (NoSuchMethodException ignored) {
        }

        return isPlugged(method);
    }

    private boolean isPlugged(Method method) {
        return pluggers.isPlugged(method);
    }
}
