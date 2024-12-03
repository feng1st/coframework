package io.codeone.framework.ext.extension;

import io.codeone.framework.ext.BizScenario;

/**
 * Provides access to {@code Extension} instances associated with specific {@link
 * BizScenario}s.
 *
 * <p>This interface is responsible for retrieving the appropriate {@code Extension}
 * implementation for a given {@code Extensible} interface and {@link BizScenario}.
 */
public interface ExtensionRepo {

    /**
     * Retrieves the {@code Extension} implementation for the specified {@code Extensible}
     * interface and {@link BizScenario}.
     *
     * @param extensibleInterface the {@code Extensible} interface to resolve
     * @param bizScenario         the {@link BizScenario} that defines the routing context
     * @return the resolved {@code Extension} instance
     * @throws IllegalArgumentException if no matching {@code Extension} is found
     */
    Object getExtension(Class<?> extensibleInterface, BizScenario bizScenario);
}
