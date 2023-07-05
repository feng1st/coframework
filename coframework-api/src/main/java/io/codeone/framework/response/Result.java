package io.codeone.framework.response;

import lombok.Data;
import lombok.experimental.Accessors;

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
@Data
@Accessors(chain = true)
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
}
