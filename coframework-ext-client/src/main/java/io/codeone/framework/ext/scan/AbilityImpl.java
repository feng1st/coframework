package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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
        this.code = classCode + "[" + bizScenario.toCode() + "]." + methodKey;
        this.implementingClass = getClassKey(implementingClass);
        this.bizScenario = bizScenario.toCode();
    }

    public String getClassCode() {
        return classCode;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public String getCode() {
        return code;
    }

    public String getImplementingClass() {
        return implementingClass;
    }

    public String getBizScenario() {
        return bizScenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbilityImpl ability = (AbilityImpl) o;
        return Objects.equals(code, ability.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
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
                + "("
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(","))
                + ")";
    }
}
