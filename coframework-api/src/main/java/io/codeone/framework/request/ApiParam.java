package io.codeone.framework.request;

import io.codeone.framework.util.ArgChecker;

/**
 * ApiParam indicates a parameter of an API call. An API is a service which is
 * exposed to outer system or customers.
 */
public interface ApiParam {

    /**
     * checkArgs() of an ApiParam will be called before the invocation of its
     * belonging method of a service if that service is annotated by @API.
     * <p>
     * An IllegalArgumentException should be thrown if any checking failed. The
     * framework provided an {@link ArgChecker} util to facilitate these
     * checking.
     */
    default void checkArgs() {
    }
}
