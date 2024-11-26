package io.codeone.framework.api.exception;

/**
 * Represents an exception with an error code for service operations.
 *
 * <p>If a service or method is annotated with {@code API}, any exception that is
 * {@code ApiException} or can be converted into {@code ApiException} using {@code ApiExceptionConverter}
 * will be transformed by the framework into a failed result with a specified code.
 *
 * <p>Additionally, if the service or method is annotated with {@code API} or {@code Logging},
 * any such exception's code will be recognized and logged by the framework.
 */
public interface ApiException {

    /**
     * Retrieves the error code.
     *
     * @return the error code
     */
    String getCode();
}
