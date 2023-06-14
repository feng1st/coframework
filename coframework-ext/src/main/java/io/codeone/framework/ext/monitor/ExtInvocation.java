package io.codeone.framework.ext.monitor;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.model.ExtensionCoordinate;

import java.lang.reflect.Method;

public class ExtInvocation {

    private final Method method;

    private final ExtensionCoordinate coordinate;

    private final BizScenarioExtension bizExt;

    public static ExtInvocation of(Method method, ExtensionCoordinate coordinate, BizScenarioExtension bizExt) {
        return new ExtInvocation(method, coordinate, bizExt);
    }

    public ExtInvocation(Method method, ExtensionCoordinate coordinate, BizScenarioExtension bizExt) {
        this.method = method;
        this.coordinate = coordinate;
        this.bizExt = bizExt;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getExtensibleClass() {
        return coordinate.getExtensibleClass();
    }

    public BizScenario getExpectedBizScenario() {
        return coordinate.getBizScenario();
    }

    public Class<?> getExtensionClass() {
        return bizExt.getExtension().getClass();
    }

    public BizScenario getActualBizScenario() {
        return bizExt.getBizScenario();
    }

    @Override
    public String toString() {
        return coordinate + "->" + bizExt + "." + method.getName() + "(...)";
    }
}
