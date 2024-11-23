package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public interface ExtensionSessionRepo {

    int INDEX_CUSTOM_RESOLVER = -1;

    int INDEX_IGNORE = -2;

    void buildParamIndex(Method method, ExtensionSession annotation);

    BizScenario resolveBizScenario(Method method, Object[] args, ExtensionSession session);
}
