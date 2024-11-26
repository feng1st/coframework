package io.codeone.framework.api.response;

/**
 * Represents the result of a service operation.
 *
 * <p>If a service or method is annotated with {@code API} or {@code Logging}, and
 * the return type is {@code ApiResult} or can be converted to {@code ApiResult}
 * using {@code ApiResultConverter}, the result's success status, data, error code,
 * and error message will be automatically recognized and logged by the framework.
 *
 * @param <T> the type of data returned by the operation
 */
public interface ApiResult<T> {

    /**
     * Indicates whether the operation was successful.
     *
     * @return true if the operation succeeded, false otherwise
     */
    boolean isSuccess();

    /**
     * Retrieves the data returned by the operation, if any.
     *
     * @return the result data, or null if none
     */
    T getData();

    /**
     * Retrieves the error code if the operation failed.
     *
     * @return the error code, or null if the operation was successful
     */
    String getErrorCode();

    /**
     * Retrieves the error message if the operation failed.
     *
     * @return the error message, or null if the operation was successful
     */
    String getErrorMessage();
}
