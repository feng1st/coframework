package io.codeone.framework.request;

import io.codeone.framework.util.ArgChecker;

/**
 * Interface of API parameters that the framework can recognize and use in
 * arguments validating automatically.
 *
 * @see BaseRequest
 */
public interface ApiParam {

    /**
     * This method will be called before the invocation of the parameter's
     * belonging method/service, if that method/service is annotated by
     * {@code @API} (in {@code coframework-core}).
     * <p>
     * You can throw an IllegalArgumentException if any validation failed. The
     * framework provided an {@link ArgChecker} util to facilitate these
     * checking.
     * <p>
     * Expose this method to clients will help them to know explicitly how to
     * supply with the argument.
     */
    default void checkArgs() {
    }
}
