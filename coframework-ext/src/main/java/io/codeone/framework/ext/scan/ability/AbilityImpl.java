package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.util.ExtUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class AbilityImpl {

    private final String classCode;

    private final String methodCode;

    private final String code;

    private final String implClass;

    private final String bizScenario;

    public static AbilityImpl of(Method method, Class<?> implClass, BizScenario bizScenario) {
        return new AbilityImpl(method, implClass, bizScenario);
    }

    public AbilityImpl(Method method, Class<?> implClass, BizScenario bizScenario) {
        String classCode = ExtUtils.getClassKey(method);
        String methodSubKey = ExtUtils.getMethodSubKey(method);
        this.classCode = classCode;
        this.methodCode = classCode + "." + methodSubKey;
        this.code = classCode + "[" + bizScenario.toCode() + "]." + methodSubKey;
        this.implClass = ExtUtils.getClassKey(implClass);
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

    public String getImplClass() {
        return implClass;
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
}
