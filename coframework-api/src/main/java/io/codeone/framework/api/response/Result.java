package io.codeone.framework.api.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The result of an API call. For example:
 * <pre>{@code
 * // a result without data, only successfulness, error code, and error message
 * Result<Void> result;
 *
 * // the same as above, but with a Long data
 * Result<Long> result;
 *
 * // a result of a list of Long
 * Result<List<Long>> result;
 *
 * // a result of a paged Long data, including pageIndex, pageSize and totalCount
 * Result<Page<Long>> result;
 * }</pre>
 *
 * @param <T> the type of returned data
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
     *
     * @param <T> the type of returned data
     * @return the successful result
     */
    public static <T> Result<T> success() {
        return new Result<T>()
                .setSuccess(true);
    }

    /**
     * Constructs a successful result with data.
     *
     * @param data the returned data
     * @param <T>  the type of returned data
     * @return the successful result
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setSuccess(true)
                .setData(data);
    }

    /**
     * Constructs a failed result with code and message. The result does not
     * contain any data of course.
     *
     * @param errorCode    the code of the error
     * @param errorMessage the message of the error
     * @param <T>          the type of returned data
     * @return the failed result
     */
    public static <T> Result<T> fail(String errorCode, String errorMessage) {
        return new Result<T>()
                .setSuccess(false)
                .setErrorCode(errorCode)
                .setErrorMessage(errorMessage);
    }
}
