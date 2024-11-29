package io.codeone.framework.ext.session;

import io.codeone.framework.ext.annotation.RouteBy;
import io.codeone.framework.ext.util.ExtUtils;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

@UtilityClass
public class ExtensionSessionParamParser {

    public int parseParamIndex(Method method, ExtensionSession session) {
        if (session != null) {
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
                return Optional.ofNullable(parseCustomResolver(session))
                        .orElseThrow(() -> new IllegalStateException(String.format(
                                "No BizScenarioResolver specified for method '%s'",
                                method)));
            }
            if (session.value() != BizScenarioResolvePolicy.AUTO) {
                return ExtensionSessionRepo.INDEX_IGNORE;
            }
        }

        Integer paramIndex;
        if ((paramIndex = parseCustomResolver(session)) != null) {
            return paramIndex;
        }
        if ((paramIndex = parseSpecified(method)) != null) {
            return paramIndex;
        }
        if ((paramIndex = parseFirst(method)) != null) {
            return paramIndex;
        }

        if (session != null) {
            throw new IllegalStateException(String.format(
                    "No BizScenario source found in method '%s'",
                    method));
        }
        return ExtensionSessionRepo.INDEX_IGNORE;
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

    private Integer parseCustomResolver(ExtensionSession session) {
        if (session != null
                && session.customResolver() != BizScenarioResolver.class) {
            return ExtensionSessionRepo.INDEX_CUSTOM_RESOLVER;
        }
        return null;
    }
}