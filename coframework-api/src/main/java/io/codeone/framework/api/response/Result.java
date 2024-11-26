package io.codeone.framework.api.response;

import lombok.Data;

/**
 * Represents a general result of a service operation.
 *
 * @param <T> the type of data returned by the operation
 */
@Data
public class Result<T> implements ApiResult<T> {

    private boolean success;

    private T data;

    private String errorCode;

    private String errorMessage;

    /**
     * Creates a successful result with no data.
     *
     * @param <T> the type of the result data
     * @return a new successful result instance
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        return result;
    }

    /**
     * Creates a successful result with data.
     *
     * @param data the data to include in the result
     * @param <T>  the type of the result data
     * @return a new successful result instance
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * Creates a failure result with an error code and message.
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @param <T>          the type of the result data
     * @return a new failure result instance
     */
    public static <T> Result<T> failure(String errorCode, String errorMessage) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }
}
