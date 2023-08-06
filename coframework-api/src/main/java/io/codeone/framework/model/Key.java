package io.codeone.framework.model;

/**
 * Represents a type-specified key of a map, and has a namespace to achieve key
 * isolation. The best practice is using enumerations to implement this
 * interface.
 */
public interface Key {

    /**
     * Returns the key, which comprises a namespace and a code.
     *
     * @return the key, which comprises a namespace and a code
     */
    default String getKey() {
        return getNamespace() + "." + getCode();
    }

    /**
     * Returns the class of the value, which is used for type validation.
     *
     * <p>Please note that it does not support parameterized type, such as
     * <code>List{@literal <}<em>Long</em>></code> or
     * <code>List{@literal <}<em>String</em>></code>.
     *
     * @return the class of the value
     */
    <T> Class<T> getClazz();

    /**
     * Returns the namespace that differentiates key sets. The default
     * implementation is using the simple name of its class.
     *
     * @return the namespace that differentiates key sets
     */
    default String getNamespace() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the code of the key. The default implementation is using
     * {@code toString()}.
     *
     * @return the code of the key
     */
    default String getCode() {
        return toString();
    }
}
