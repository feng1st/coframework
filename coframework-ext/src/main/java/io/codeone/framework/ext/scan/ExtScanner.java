package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

/**
 * Scans all Extensions and Extensible in the system.
 */
public interface ExtScanner {

    /**
     * Lets you process an Extensible interface the framework found.
     *
     * @param extensibleClass the Extensible interface the framework found
     */
    default void scanExtensible(Class<?> extensibleClass) {
    }

    /**
     * Lets you process an Extensible method (method of an Extensible interface)
     * the framework found.
     *
     * @param extensibleClass the Extensible interface the framework found
     * @param method          one of the Extensible interface methods
     */
    default void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
    }

    /**
     * Lets you process an Extension instance the framework found for a
     * particular Extensible method. The scanning is drilled down to method
     * level because the Extensible methods may be implemented by different
     * classes/abstract classes in the class hierarchy. Please refer to
     * {@link AbilityImpl#getImplementingClass()} and
     * {@link AbilityImpl#getBizScenario()} for more details.
     *
     * @param extensibleClass   the Extensible interface the Extension instance
     *                          implements for
     * @param method            one of the Extensible interface methods
     * @param implementingClass the actual class that implemented the Extensible
     *                          interface method
     * @param bizScenario       the actual {@link BizScenario} the implemented
     *                          method is applied for
     */
    default void scanExtension(Class<?> extensibleClass, Method method,
                               Class<?> implementingClass, BizScenario bizScenario) {
    }
}
