package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public interface ExtensionSessionIndexer {

    void index(Method method, ExtensionSession annotation);

    BizScenario resolve(Method method, Object[] args, ExtensionSession session);
}
