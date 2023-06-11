package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.model.BizScenarioExtension;

public interface ExtensionRepo {

    void putExtension(Class<?> extensibleClass, BizScenario bizScenario, Object ext);

    BizScenarioExtension getExtension(Class<?> extensibleClass, BizScenario bizScenario);
}
