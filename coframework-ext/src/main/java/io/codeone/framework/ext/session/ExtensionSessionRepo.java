package io.codeone.framework.ext.session;

import java.lang.reflect.Method;

/**
 * Repository for managing the resolution of {@code BizScenario} parameters in methods
 * annotated with {@link ExtensionSession}.
 *
 * <p>Determines the index of {@code BizScenario} parameters in methods based on
 * specified resolution policies.
 */
public interface ExtensionSessionRepo {

    /**
     * Index indicating that a custom resolver is used for determining the {@code
     * BizScenario}.
     */
    int INDEX_CUSTOM_RESOLVER = -1;

    /**
     * Index indicating that no resolution should occur for {@code BizScenario}.
     */
    int INDEX_IGNORE = -2;

    /**
     * Builds the parameter index for a method based on the associated {@link ExtensionSession}
     * annotation.
     *
     * @param method     the method to process
     * @param annotation the {@link ExtensionSession} annotation
     */
    void buildParamIndex(Method method, ExtensionSession annotation);

    /**
     * Retrieves the index of the parameter representing the {@code BizScenario}
     * in the given method.
     *
     * @param method the method to analyze
     * @return the parameter index, or {@link #INDEX_CUSTOM_RESOLVER} or {@link
     * #INDEX_IGNORE}
     */
    int getParamIndex(Method method);
}
