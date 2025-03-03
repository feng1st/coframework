package io.codeone.framework.ext.session;

import io.codeone.framework.common.util.TypeStringUtils;
import io.codeone.framework.ext.annotation.ExtensionSession;
import io.codeone.framework.ext.annotation.RouteBy;
import io.codeone.framework.ext.util.ExtUtils;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for parsing the parameter index of {@code BizScenario} in methods
 * annotated with {@link ExtensionSession}.
 *
 * <p>Supports different resolution policies defined in {@link BizScenarioResolvePolicy}.
 */
@UtilityClass
public class ExtensionSessionParamParser {

    /**
     * Parses the parameter index for the {@code BizScenario} parameter in the given
     * method.
     *
     * @param method  the method to analyze
     * @param session the {@link ExtensionSession} annotation on the method
     * @return the parameter index or {@link ExtensionSessionRepo#INDEX_CUSTOM_RESOLVER}
     * or {@link ExtensionSessionRepo#INDEX_IGNORE}
     */
    public int parseParamIndex(Method method, ExtensionSession session) {
        Objects.requireNonNull(session);
        if (session.value() == BizScenarioResolvePolicy.AUTO) {
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

            return ExtensionSessionRepo.INDEX_IGNORE;
        }

        if (session.value() == BizScenarioResolvePolicy.FIRST) {
            return Optional.ofNullable(parseFirst(method))
                    .orElseThrow(() -> new IllegalStateException(String.format(
                            "No parameter of type BizScenarioParam found in method \"%s\". Ensure the method has at least one parameter of this type.",
                            TypeStringUtils.toString(method))));
        }
        if (session.value() == BizScenarioResolvePolicy.LAST) {
            return Optional.ofNullable(parseLast(method))
                    .orElseThrow(() -> new IllegalStateException(String.format(
                            "No parameter of type BizScenarioParam found in method \"%s\". Ensure the method has at least one parameter of this type.",
                            TypeStringUtils.toString(method))));
        }
        if (session.value() == BizScenarioResolvePolicy.SPECIFIED) {
            return Optional.ofNullable(parseSpecified(method))
                    .orElseThrow(() -> new IllegalStateException(String.format(
                            "No parameter annotated with @RouteBy found in method \"%s\". Ensure the method has exactly one parameter annotated with @RouteBy.",
                            TypeStringUtils.toString(method))));
        }
        if (session.value() == BizScenarioResolvePolicy.CUSTOM) {
            return Optional.ofNullable(parseCustomResolver(session))
                    .orElseThrow(() -> new IllegalStateException(String.format(
                            "No custom BizScenarioResolver specified in @ExtensionSession for method \"%s\". Ensure the annotation defines a valid resolver.",
                            TypeStringUtils.toString(method))));
        }

        // session.value() == BizScenarioResolvePolicy.IGNORE
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
                            "Duplicate @RouteBy annotations found in method \"%s\". Ensure the method has at most one parameter annotated with @RouteBy.",
                            TypeStringUtils.toString(method)));
                }
                if (!ExtUtils.isBizScenarioParam(param.getType())) {
                    throw new IllegalStateException(String.format(
                            "Parameter annotated with @RouteBy in method \"%s\" is not of type BizScenarioParam. Ensure the parameter type is BizScenarioParam.",
                            TypeStringUtils.toString(method)));
                }
                index = i;
            }
        }
        return index;
    }

    private Integer parseCustomResolver(ExtensionSession session) {
        if (session.customResolver() != BizScenarioResolver.class) {
            return ExtensionSessionRepo.INDEX_CUSTOM_RESOLVER;
        }
        return null;
    }
}
