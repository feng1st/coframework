package io.codeone.framework.ext.monitor;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.model.BizScenarioExtension;

import java.lang.reflect.Method;

public interface ExtInvocationMonitor {

    void monitor(Class<?> extensibleClass, Method method, BizScenario bizScenario, BizScenarioExtension bizExt);
}
