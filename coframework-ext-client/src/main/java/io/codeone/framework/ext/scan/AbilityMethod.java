package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbilityMethod {

    private final String classCode;

    private final String code;

    private final String name;

    private final String description;

    private final int order;

    public static AbilityMethod of(Ability.Method abilityMethod, Method method) {
        return new AbilityMethod(abilityMethod, method);
    }

    public AbilityMethod(Ability.Method abilityMethod, Method method) {
        String classCode = getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + getMethodKey(method);
        this.name = getName(abilityMethod, method);
        this.description = getDescription(abilityMethod);
        this.order = getOrder(abilityMethod);
    }

    public String getClassCode() {
        return classCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbilityMethod that = (AbilityMethod) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return name;
    }

    private static String getClassKey(Method method) {
        return method.getDeclaringClass().getName();
    }

    private static String getMethodKey(Method method) {
        return method.getName()
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(",", "(", ")"));
    }

    private static String getName(Ability.Method abilityMethod, Method method) {
        return (abilityMethod == null || abilityMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : abilityMethod.name();
    }

    private static String getDescription(Ability.Method abilityMethod) {
        return abilityMethod == null ? "" : abilityMethod.description();
    }

    private static int getOrder(Ability.Method abilityMethod) {
        return abilityMethod == null ? -1 : abilityMethod.order();
    }
}
