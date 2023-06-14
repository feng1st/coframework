package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.util.ExtUtils;

import java.lang.reflect.Method;
import java.util.Objects;

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
        String classCode = ExtUtils.getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + ExtUtils.getMethodSubKey(method);
        this.name = (abilityMethod == null || abilityMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : abilityMethod.name();
        this.description = abilityMethod == null ? "" : abilityMethod.description();
        this.order = abilityMethod == null ? -1 : abilityMethod.order();
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
}
