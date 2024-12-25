package io.codeone.framework.ext.session;

import io.codeone.framework.ext.annotation.ExtensionSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link ExtensionSessionRepo} that resolves the parameter index
 * for {@code BizScenario} in methods.
 *
 * <p>This implementation uses a cache to store and retrieve parameter indices for
 * methods. It leverages {@link BizScenarioResolverCache} for custom resolvers defined
 * in {@link ExtensionSession}.
 */
@Component
public class ExtensionSessionRepoImpl implements ExtensionSessionRepo {

    @Autowired
    private BizScenarioResolverCache bizScenarioResolverCache;

    private final Map<Method, Integer> paramIndexMap = new HashMap<>();

    @Override
    public void buildParamIndex(Method method, ExtensionSession session) {
        Objects.requireNonNull(session);
        paramIndexMap.computeIfAbsent(method, k -> {
            int index = ExtensionSessionParamParser.parseParamIndex(method, session);
            if (index == INDEX_CUSTOM_RESOLVER) {
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
