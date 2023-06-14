package io.codeone.framework.request;

/**
 * Indicates this is a parameter of an API.
 * <p>
 * An API is a Service which is exposed to outer system. This framework provided some facilities to utilize the using
 * of APIs.
 */
public interface ApiParam {

    /**
     * checkArgs() of an ApiParam will be called upon the invocation of its belonging method of a Service if that
     * Service is annotated by @API.
     */
    default void checkArgs() {
    }
}
