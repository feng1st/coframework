package io.codeone.framework.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * This class provides paginated results according to specific interface conventions.
 *
 * <p>Note that {@code PageResult} is not an {@code ApiResult}. The only native
 * {@code ApiResult} supported by this framework is {@code Result}. It is recommended
 * to return paginated results in the format of {@code Result<Page<T>>}. {@code
 * PageResult<T>} is used solely to adhere to certain interface conventions.
 *
 * @param <T> the type of data in the paginated result
 * @see Page
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageResult<T> extends Page<T> {

    private boolean success;

    private String errorCode;

    private String errorMessage;

    /**
     * Creates a successful paginated result from the given {@link Page}.
     *
     * @param page the page data to use
     * @param <T>  the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(Page<T> page) {
        return success(page.getData(), page.getPageIndex(), page.getPageSize(), page.getTotalCount());
    }

    /**
     * Creates an empty successful paginated result with the specified page index
     * and size.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @param <T>       the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(int pageIndex, int pageSize) {
        return success(null, pageIndex, pageSize, null);
    }

    /**
     * Creates a successful paginated result with the specified data, page index,
     * and size.
     *
     * @param data      the list of data items
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @param <T>       the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(List<T> data, int pageIndex, int pageSize) {
        return success(data, pageIndex, pageSize, null);
    }

    /**
     * Creates a successful paginated result with the specified data, page index,
     * size, and total count.
     *
     * @param data       the list of data items
     * @param pageIndex  the page index
     * @param pageSize   the page size
     * @param totalCount the total number of items available
     * @param <T>        the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(List<T> data, int pageIndex, int pageSize, Long totalCount) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        result.setTotalCount(totalCount);
        return result;
    }

    /**
     * Creates a failure paginated result with an error code and message.
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @param <T>          the type of the data items
     * @return a new failure {@code PageResult} instance
     */
    public static <T> PageResult<T> failure(String errorCode, String errorMessage) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }
}
