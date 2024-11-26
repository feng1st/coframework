package io.codeone.framework.api.exception;

/**
 * Represents an exception with an error code for service operations.
 *
 * <p>Service operation exceptions will be recognized and logged by the framework
 * if the type is {@code ApiException} or can be converted to {@code ApiException}
 * using {@code ApiExceptionConverter}.
 */
public interface ApiException {

    /**
     * Retrieves the error code.
     *
     * @return the error code
     */
    String getCode();
}
