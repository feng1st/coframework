package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class AbilityImpl {

    private final String classCode;

    private final String methodCode;

    private final String code;

    private final String implementingClass;

    private final String bizScenario;

    public static AbilityImpl of(Method method, Class<?> implementingClass, BizScenario bizScenario) {
        return new AbilityImpl(method, implementingClass, bizScenario);
    }

    public AbilityImpl(Method method, Class<?> implementingClass, BizScenario bizScenario) {
        String classCode = getClassKey(method);
        String methodKey = getMethodKey(method);
        this.classCode = classCode;
        this.methodCode = classCode + "." + methodKey;
        this.code = classCode + "[" + bizScenario.getCode() + "]." + methodKey;
        this.implementingClass = getClassKey(implementingClass);
        this.bizScenario = bizScenario.getCode();
    }

    @Override
    public String toString() {
        return code;
    }

    private static String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    private static String getClassKey(Method method) {
        return getClassKey(method.getDeclaringClass());
    }

    private static String getMethodKey(Method method) {
        return method.getName()
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(",", "(", ")"));
    }
}
