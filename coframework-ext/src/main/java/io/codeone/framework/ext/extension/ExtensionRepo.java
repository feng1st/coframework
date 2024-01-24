package io.codeone.framework.ext.extension;

import io.codeone.framework.ext.BizScenario;

/**
 * Repository to store and access the Extension implementation mapping. The
 * mapping key is {@link ExtensionCoordinate} and the value is an Extension
 * instance.
 *
 * @see ExtensionRegister
 */
public interface ExtensionRepo {

    /**
     * Saves an Extension implementation mapping.
     *
     * @param coordinate the key which comprises the target Extensible interface
     *                   and the particular {@link BizScenario} the Extension
     *                   implementation implements for
     * @param ext        the Extension implementation instance
     */
    void putExtension(ExtensionCoordinate coordinate, Object ext);

    /**
     * Caches and returns the Extension implementation instance for the
     * expected {@link ExtensionCoordinate}. The {@code BizScenario} of the
     * returned Extension instance may be different from the {@code BizScenario}
     * of the argument since a {@code BizScenario} looking up may occur.
     *
     * <p>A {@link BizScenario} looking up will occur in this method if the
     * exact {@code ExtensionCoordinate#getBizScenario()} did not have an
     * associated Extension instance. The framework will iterate next broader
     * {@code BizScenario}s and try to find a match, and throw an exception if
     * no match was found.
     *
     * <p>The cache in this method is a least-recently-used cache since the
     * {@link BizScenario} object inside the argument which will compose the key
     * of the cache is given by the users and may be arbitrary, and we use LRU
     * cache to prevent potential OOM.
     *
     * @param coordinate the key which comprises the target Extensible interface
     *                   and the expected {@link BizScenario} to look for
     * @return compound of the Extension implementation instance and the actual
     * {@link BizScenario} it implemented for
     */
    BizScenarioExtension getExtension(ExtensionCoordinate coordinate);
}
