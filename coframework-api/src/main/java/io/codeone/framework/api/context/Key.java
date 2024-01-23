package io.codeone.framework.api.context;

/**
 * Represents a type-specified key of a map, and has a namespace to achieve key
 * isolation. The best practice is using enumerations to implement this
 * interface.
 */
public interface Key {

    /**
     * Returns the class of the value, which is used for type validation.
     *
     * <p>Please note that it does not support parameterized type, such as
     * <code>List{@literal <}<em>Long</em>></code> or
     * <code>List{@literal <}<em>String</em>></code>.
     *
     * @param <T> the class of this key
     * @return the class of the value
     */
    <T> Class<T> getClazz();

    default <T> T cast(Object value) {
        return this.<T>getClazz().cast(value);
    }
}
