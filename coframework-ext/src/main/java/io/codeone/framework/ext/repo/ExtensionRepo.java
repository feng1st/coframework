package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.BizScenario;

public interface ExtensionRepo {

    void putExtension(Class<?> extensibleClass, BizScenario bizScenario, Object ext);

    Object getExtension(Class<?> extensibleClass, BizScenario bizScenario);
}
