package io.codeone.framework.api.response;

/**
 * Represents the result of a service operation.
 *
 * <p>Service operation results, including success status, data, error code, and
 * error message, will be recognized and logged by the framework if the type is
 * {@code ApiResult} or can be converted to {@code ApiResult} using {@code ApiResultConverter}.
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
