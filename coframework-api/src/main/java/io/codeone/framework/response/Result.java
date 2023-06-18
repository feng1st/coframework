package io.codeone.framework.response;

import java.util.Objects;

/**
 * The result of an API call. For example:
 * <pre>
 * {@code
 * Result<Void> result;        // A result without data, only successfulness,
 *                             // code, etc.
 * Result<Long> result;        // A Long result, and successfulness and other
 *                             // properties.
 * Result<List<Long>> result;  // A result of a list of Long.
 * Result<Page<Long>> result;  // A result of a paged Long, including
 *                             // pageIndex, pageSize and totalCount.
 * }
 * </pre>
 *
 * @param <T> The type of returned data.
 */
public class Result<T> {

    /**
     * The successfulness of the API call.
     */
    private boolean success;

    /**
     * Returned data.
     */
    private T data;

    /**
     * The error code if the call had failed.
     */
    private String errorCode;

    /**
     * The error message if the call had failed.
     */
    private String errorMessage;

    /**
     * Constructs a successful result with no data.
     */
    public static <T> Result<T> success() {
        return new Result<T>()
                .setSuccess(true);
    }

    /**
     * Constructs a successful result with data.
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setSuccess(true)
                .setData(data);
    }

    /**
     * Constructs a failed result with code and message.
     */
    public static <T> Result<T> fail(String errorCode, String errorMessage) {
        return new Result<T>()
                .setSuccess(false)
                .setErrorCode(errorCode)
                .setErrorMessage(errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Result<T> setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Result<T> setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result<?> result = (Result<?>) o;
        return success == result.success
                && Objects.equals(data, result.data)
                && Objects.equals(errorCode, result.errorCode)
                && Objects.equals(errorMessage, result.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, data, errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
