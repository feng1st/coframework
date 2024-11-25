package io.codeone.framework.ext.session;

import java.lang.reflect.Method;

public interface ExtensionSessionRepo {

    int INDEX_CUSTOM_RESOLVER = -1;

    int INDEX_IGNORE = -2;

    void buildParamIndex(Method method, ExtensionSession annotation);

    int getParamIndex(Method method);
}
