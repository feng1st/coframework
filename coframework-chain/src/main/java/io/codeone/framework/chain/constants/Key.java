package io.codeone.framework.chain.constants;

import io.codeone.framework.chain.model.Data;

/**
 * Represents an input/output parameter of the nodes, or the key of the target
 * if its type is {@link Data}.
 * <p>
 * The best practice is using enumerations that implemented this interface.
 */
public interface Key {

    /**
     * The key, which comprises a namespace and a code.
     */
    default String getKey() {
        return getNamespace() + "." + getCode();
    }

    /**
     * The class of the value, which is used for type validation.
     * <p>
     * Please note that it does not support parameterized type, such as
     * List<<b>Long</b>> or List<<b>String</b>>.
     */
    <T> Class<T> getClazz();

    /**
     * The namespace of the key.
     */
    default String getNamespace() {
        return getClass().getSimpleName();
    }

    /**
     * The code of the key.
     */
    default String getCode() {
        return toString();
    }
}
