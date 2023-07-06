package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@EqualsAndHashCode
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
        String classCode = ScanModelUtils.getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + ScanModelUtils.getMethodKey(method);
        this.name = ScanModelUtils.getName(abilityMethod, method);
        this.description = ScanModelUtils.getDescription(abilityMethod);
        this.order = ScanModelUtils.getOrder(abilityMethod);
    }

    @Override
    public String toString() {
        return name;
    }
}
