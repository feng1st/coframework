package io.codeone.framework.chain.spec;

/**
 * Used in {@link ChainSpec}, and its {@link #asString()} will be logged as the
 * name of the chain.
 *
 * <p>The best practice is to use enumerations that implemented this interface.
 */
public interface ChainName {

    default String asString() {
        return getClass().getSimpleName() + "." + this;
    }
}
