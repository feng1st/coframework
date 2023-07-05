package io.codeone.framework.ext.monitor;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.model.ExtensionCoordinate;
import lombok.Data;

import java.lang.reflect.Method;

@Data(staticConstructor = "of")
public class ExtInvocation {

    private final Method method;

    private final ExtensionCoordinate coordinate;

    private final BizScenarioExtension bizExt;

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
