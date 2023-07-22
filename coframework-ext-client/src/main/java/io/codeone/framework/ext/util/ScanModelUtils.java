package io.codeone.framework.ext.util;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtensionPoint;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class ScanModelUtils {

    public String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    public String getClassKey(Method method) {
        return method.getDeclaringClass().getName();
    }

    public String getMethodKey(Method method) {
        return method.getName()
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(",", "(", ")"));
    }

    public String getName(Ability ability, Class<?> extensibleClass) {
        return ability.name().isEmpty() ? extensibleClass.getSimpleName() : ability.name();
    }

    public String getName(Ability.Method abilityMethod, Method method) {
        return (abilityMethod == null || abilityMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : abilityMethod.name();
    }

    public String getDescription(Ability.Method abilityMethod) {
        return abilityMethod == null ? "" : abilityMethod.description();
    }

    public int getOrder(Ability.Method abilityMethod) {
        return abilityMethod == null ? -1 : abilityMethod.order();
    }

    public String getName(ExtensionPoint extPt, Class<?> extensibleClass) {
        return extPt.name().isEmpty() ? extensibleClass.getSimpleName() : extPt.name();
    }

    public String getName(ExtensionPoint.Method extPtMethod, Method method) {
        return (extPtMethod == null || extPtMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : extPtMethod.name();
    }

    public String getDescription(ExtensionPoint.Method extPtMethod) {
        return extPtMethod == null ? "" : extPtMethod.description();
    }

    public int getOrder(ExtensionPoint.Method extPtMethod) {
        return extPtMethod == null ? -1 : extPtMethod.order();
    }
}
