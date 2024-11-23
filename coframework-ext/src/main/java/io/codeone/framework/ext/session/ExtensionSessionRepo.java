package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public interface ExtensionSessionRepo {

    // FIXME
    int INDEX_CUSTOM = -1;

    // FIXME
    int INDEX_IGNORE = -2;

    void index(Method method, ExtensionSession annotation);

    BizScenario resolve(Method method, Object[] args, ExtensionSession session);
}
