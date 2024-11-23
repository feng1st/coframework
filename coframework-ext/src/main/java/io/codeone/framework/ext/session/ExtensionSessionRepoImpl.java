package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.RouteBy;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ExtensionSessionRepoImpl implements ExtensionSessionRepo {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<Method, Integer> paramIndexMap = new HashMap<>();

    private final Map<Class<? extends BizScenarioResolver>, BizScenarioResolver> resolverMap
            = new ConcurrentHashMap<>();

    @Override
    public void buildParamIndex(Method method, ExtensionSession annotation) {
        paramIndexMap.computeIfAbsent(method, k -> parseParamIndex(method, annotation));
    }

    @Override
    public BizScenario resolveBizScenario(Method method, Object[] args, ExtensionSession session) {
        Integer index = paramIndexMap.get(method);
        if (index == null) {
            throw new IllegalStateException(String.format(
                    "No BizScenario found for method '%s'",
                    method));
        }

        if (index == INDEX_IGNORE) {
            return null;
        }

        if (index >= 0) {
            BizScenarioParam bizScenarioParam = (BizScenarioParam) args[index];
            if (bizScenarioParam != null && bizScenarioParam.getBizScenario() != null) {
                return bizScenarioParam.getBizScenario();
            }
            if (session != null) {
                throw new IllegalStateException(String.format(
                        "BizScenario is null for parameter %d in method '%s'",
                        index,
                        method));
            }
        }

        if (session == null) {
            return null;
        }

        BizScenarioResolver resolver;
        try {
            resolver = resolverMap.computeIfAbsent(session.customResolver(), k -> applicationContext.getBean(k));
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "Cannot load BizScenarioResolver '%s'",
                    session.customResolver().getSimpleName()));
        }

        BizScenario bizScenario = resolver.resolve(args);
        if (bizScenario == null) {
            throw new IllegalStateException(String.format(
                    "BizScenario could not be resolved using resolver '%s' for method '%s'",
                    session.customResolver().getSimpleName(),
                    method));
        }
        return bizScenario;
    }

    private int parseParamIndex(Method method, ExtensionSession session) {
        if (session != null) {
            if (session.value() == BizScenarioResolvePolicy.IGNORE) {
                return INDEX_IGNORE;
            }
            if (session.value() == BizScenarioResolvePolicy.FIRST) {
                return Optional.ofNullable(parseFirst(method))
                        .orElseThrow(() -> new IllegalStateException(String.format(
                                "No BizScenarioParam found in method '%s'",
                                method)));
            }
            if (session.value() == BizScenarioResolvePolicy.LAST) {
                return Optional.ofNullable(parseLast(method))
                        .orElseThrow(() -> new IllegalStateException(String.format(
                                "No BizScenarioParam found in method '%s'",
                                method)));
            }
            if (session.value() == BizScenarioResolvePolicy.SPECIFIED) {
                return Optional.ofNullable(parseSpecified(method))
                        .orElseThrow(() -> new IllegalStateException(String.format(
                                "No @RouteBy BizScenarioParam found in method '%s'",
                                method)));
            }
            if (session.value() == BizScenarioResolvePolicy.CUSTOM) {
                return Objects.requireNonNull(parseCustomResolver(method, session));
            }
        }

        Integer paramIndex = parseCustomResolver(method, session);
        if (paramIndex != null) {
            return paramIndex;
        }
        paramIndex = parseSpecified(method);
        if (paramIndex != null) {
            return paramIndex;
        }
        paramIndex = parseFirst(method);
        if (paramIndex != null) {
            return paramIndex;
        }

        // session.value() == BizScenarioResolvePolicy.AUTO
        if (session != null) {
            throw new IllegalStateException(String.format(
                    "No BizScenarioParam found in method '%s'",
                    method));
        }
        return INDEX_IGNORE;
    }

    private Integer parseFirst(Method method) {
        for (int i = 0; i < method.getParameters().length; i++) {
            if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                return i;
            }
        }
        return null;
    }

    private Integer parseLast(Method method) {
        for (int i = method.getParameters().length - 1; i >= 0; i--) {
            if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                return i;
            }
        }
        return null;
    }

    private Integer parseSpecified(Method method) {
        Integer index = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.isAnnotationPresent(RouteBy.class)) {
                if (index != null) {
                    throw new IllegalStateException(String.format(
                            "Duplicate @RouteBy found in method '%s'",
                            method));
                }
                if (!ExtUtils.isBizScenarioParam(param.getType())) {
                    throw new IllegalStateException(String.format(
                            "Parameter with @RouteBy in method '%s' is not BizScenarioParam",
                            method));
                }
                index = i;
            }
        }
        return index;
    }

    private Integer parseCustomResolver(Method method, ExtensionSession session) {
        if (session == null) {
            return null;
        }
        if (session.customResolver() == BizScenarioResolver.class) {
            if (session.value() == BizScenarioResolvePolicy.CUSTOM) {
                throw new IllegalStateException(String.format(
                        "No BizScenarioResolver specified for method '%s'",
                        method));
            }
            return null;
        }
        try {
            applicationContext.getBean(session.customResolver());
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "BizScenarioResolver not found for method '%s'",
                    method));
        }
        return INDEX_CUSTOM_RESOLVER;
    }
}
