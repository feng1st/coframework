package io.codeone.framework.ext.extension;

import io.codeone.framework.ext.BizScenario;

public interface ExtensionRepo {

    Object getExtension(Class<?> extensibleInterface, BizScenario bizScenario);
}
