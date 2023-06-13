package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtMethod;

import java.lang.reflect.Method;

public class AbilityImplInfo {

    private final Ability ability;

    private final ExtMethod extMethod;

    private final Class<?> extensibleClass;

    private final Method method;

    private final Class<?> implementingClass;

    private final BizScenario workingBizScenario;

    public static AbilityImplInfo of(Ability ability, ExtMethod extMethod,
                                     Class<?> extensibleClass, Method method,
                                     Class<?> implementingClass, BizScenario workingBizScenario) {
        return new AbilityImplInfo(ability, extMethod, extensibleClass, method,
                implementingClass, workingBizScenario);
    }

    public AbilityImplInfo(Ability ability, ExtMethod extMethod,
                           Class<?> extensibleClass, Method method,
                           Class<?> implementingClass, BizScenario workingBizScenario) {
        this.ability = ability;
        this.extMethod = extMethod;
        this.extensibleClass = extensibleClass;
        this.method = method;
        this.implementingClass = implementingClass;
        this.workingBizScenario = workingBizScenario;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getImplementingClass() {
        return implementingClass;
    }

    public BizScenario getWorkingBizScenario() {
        return workingBizScenario;
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
        return implementingClass.toString();
    }
}
