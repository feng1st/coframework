package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtMethod;

import java.lang.reflect.Method;

public class AbilityMethodInfo {

    private final Ability ability;

    private final ExtMethod extMethod;

    private final Class<?> extensibleClass;

    private final Method method;

    public static AbilityMethodInfo of(Ability ability, ExtMethod extMethod,
                                       Class<?> extensibleClass, Method method) {
        return new AbilityMethodInfo(ability, extMethod, extensibleClass, method);
    }

    public AbilityMethodInfo(Ability ability, ExtMethod extMethod,
                             Class<?> extensibleClass, Method method) {
        this.ability = ability;
        this.extMethod = extMethod;
        this.extensibleClass = extensibleClass;
        this.method = method;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        if (extMethod != null && !extMethod.name().isEmpty()) {
            return extMethod.name();
        }
        return extensibleClass.getSimpleName() + "." + method.getName();
    }

    public String getDescription() {
        return extMethod == null ? "" : extMethod.description();
    }

    public int getOrder() {
        return extMethod == null ? -1 : extMethod.order();
    }

    @Override
    public String toString() {
        return method.toString();
    }
}
