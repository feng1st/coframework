package io.codeone.framework.model;

/**
 * Represents a type specified key of a map, and has a namespace to achieve key
 * isolation.
 * <p>
 * The best practice is to use enumerations to implement this interface.
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
