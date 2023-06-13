package io.codeone.framework.ext.monitor;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.repo.BizScenarioExtension;

import java.lang.reflect.Method;

public class ExtInvocationInfo {

    private final Class<?> extensibleClass;

    private final Method method;

    private final BizScenario bizScenario;

    private final BizScenarioExtension bizExt;

    public static ExtInvocationInfo of(Class<?> extensibleClass, Method method, BizScenario bizScenario,
                                       BizScenarioExtension bizExt) {
        return new ExtInvocationInfo(extensibleClass, method, bizScenario, bizExt);
    }

    public ExtInvocationInfo(Class<?> extensibleClass, Method method, BizScenario bizScenario,
                             BizScenarioExtension bizExt) {
        this.extensibleClass = extensibleClass;
        this.method = method;
        this.bizScenario = bizScenario;
        this.bizExt = bizExt;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public Method getMethod() {
        return method;
    }

    public BizScenario getExpectedBizScenario() {
        return bizScenario;
    }

    public Class<?> getExtensionClass() {
        return bizExt.getExtension().getClass();
    }

    public BizScenario getActualBizScenario() {
        return bizExt.getBizScenario();
    }

    @Override
    public String toString() {
        return extensibleClass.getSimpleName() + "[" + bizScenario + "]." + method.getName() + "->" + bizExt;
    }
}
