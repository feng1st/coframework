package io.codeone.framework.chain.context;

/**
 * Represents a type-aware object that provides casting functionality.
 *
 * <p>Classes implementing this interface expose their type via {@link #getType()}
 * and provide utilities to cast objects to the defined type.
 */
public interface Typed {

    /**
     * Retrieves the type represented by this object.
     *
     * @param <T> the type represented
     * @return the {@link Class} of the type
     */
    <T> Class<T> getType();

    /**
     * Casts the given object to the type represented by this object.
     *
     * @param obj the object to cast
     * @param <T> the target type
     * @return the cast object
     */
    default <T> T cast(Object obj) {
        return this.<T>getType().cast(obj);
    }
}
