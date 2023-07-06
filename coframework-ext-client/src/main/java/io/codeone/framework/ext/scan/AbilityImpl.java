package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

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
        String classCode = ScanModelUtils.getClassKey(method);
        String methodKey = ScanModelUtils.getMethodKey(method);
        this.classCode = classCode;
        this.methodCode = classCode + "." + methodKey;
        this.code = classCode + "[" + bizScenario.getCode() + "]." + methodKey;
        this.implementingClass = ScanModelUtils.getClassKey(implementingClass);
        this.bizScenario = bizScenario.getCode();
    }

    @Override
    public String toString() {
        return code;
    }
}
