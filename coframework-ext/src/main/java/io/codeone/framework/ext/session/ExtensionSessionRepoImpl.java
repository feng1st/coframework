package io.codeone.framework.ext.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ExtensionSessionRepoImpl implements ExtensionSessionRepo {

    @Autowired
    private BizScenarioResolverCache bizScenarioResolverCache;

    private final Map<Method, Integer> paramIndexMap = new HashMap<>();

    @Override
    public void buildParamIndex(Method method, ExtensionSession session) {
        paramIndexMap.computeIfAbsent(method, k -> {
            int index = ExtensionSessionParamParser.parseParamIndex(method, session);
            if (index == INDEX_CUSTOM_RESOLVER) {
                Objects.requireNonNull(session);
                BizScenarioResolver ignored = bizScenarioResolverCache.getResolver(session.customResolver());
            }
            return index;
        });
    }

    @Override
    public int getParamIndex(Method method) {
        Integer index = paramIndexMap.get(method);
        if (index == null) {
            throw new IllegalStateException(String.format(
                    "No BizScenario source found for method '%s'",
                    method));
        }
        return index;
    }
}
