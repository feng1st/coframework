package io.codeone.framework.ext.monitor;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.model.ExtensionCoordinate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

/**
 * This model is for Extension invocation monitoring.
 */
@RequiredArgsConstructor(staticName = "of")
public class ExtInvocation {

    @Getter
    private final Method method;

    private final ExtensionCoordinate coordinate;

    private final BizScenarioExtension bizExt;

    /**
     * Returns the Extensible interface of this invocation.
     *
     * @return the Extensible interface of this invocation
     */
    public Class<?> getExtensibleClass() {
        return coordinate.getExtensibleClass();
    }

    /**
     * Returns the passed in or resolved {@link BizScenario} argument.
     *
     * @return the passed in or resolved {@code BizScenario} argument
     */
    public BizScenario getExpectedBizScenario() {
        return coordinate.getBizScenario();
    }

    /**
     * Returns the actual routed Extension class for the Extensible interface
     * and the {@link BizScenario} argument.
     *
     * @return the actual routed Extension class for the Extensible interface
     * and the {@code BizScenario} argument
     */
    public Class<?> getExtensionClass() {
        return bizExt.getExtension().getClass();
    }

    /**
     * Returns the {@link BizScenario} stated on the first present
     * {@link Extension} annotation of the routed Extension class or its
     * superclasses (bottom up).
     *
     * @return the {@code BizScenario} stated on the {@code Extension}
     * annotation of the routed Extension class or superclass
     */
    public BizScenario getActualBizScenario() {
        return bizExt.getBizScenario();
    }

    @Override
    public String toString() {
        return coordinate + "->" + bizExt + "." + method.getName() + "(...)";
    }
}
